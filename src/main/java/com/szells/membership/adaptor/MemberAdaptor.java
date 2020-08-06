package com.szells.membership.adaptor;

import com.szells.membership.constants.Constants;
import com.szells.membership.domain.MemberDomain;
import com.szells.membership.domain.payload.ActivateMemberPayload;
import com.szells.membership.domain.payload.CancelRequestPayload;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.membership.exception.MemberNotFoundException;
import com.szells.membership.exception.MemberSaveException;
import com.szells.membership.mapper.IMemberMapper;
import com.szells.membership.repository.IMemberRepository;
import com.szells.util.cache.VisibilityScopeCache;
import com.szells.util.domain.RequestHeader;
import com.szells.util.domain.ServiceParameters;
import com.szells.util.domain.VisibilityScopeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class MemberAdaptor implements IMemberAdaptor {

    private final IMemberRepository memberRepository;
    private final IMemberMapper memberMapper;

    @Autowired
    public MemberAdaptor(IMemberRepository memberRepository,
                         IMemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public Mono<Long> findMemberIdForRefNum(ServiceParameters<String> parameters) {

        return memberRepository.findIdByExtMemberRef(parameters.getParameters()
                .getParam(Constants.EXTERNAL_MEMBER_REF_NO), Long.parseLong(parameters.getHeaders().getVisibilityScopeId()));
    }

    public Mono<MemberDomain> findActiveMemberId(ServiceParameters<String> parameters) {
        AtomicBoolean isActive = new AtomicBoolean(false);
        VisibilityScopeCache.getInstance()
                .getCacheEntry(parameters.getHeaders().getVisibilityScopeKey())
                .map(VisibilityScopeData::isH2NGClient)
                .ifPresent(isActive::set);
        return memberRepository.findMemberById(Long.parseLong(parameters.getParameters().getParam(Constants.MEMBER_ID)),
                Long.parseLong(parameters.getHeaders().getVisibilityScopeId()), isActive.get())
                .map(memberMapper::toMemberDomain);
    }

    @Override
    public Mono<MemberDomain> findMemberIdFromEmailAddress(ActivateMemberPayload activateMemberPayload) {
        log.info("activateMemberProfilePayload = {}", activateMemberPayload);
        return memberRepository.findMemberByEmailIdByActivationCd(activateMemberPayload.getEmailAddress()).map(memberMapper::toMemberDomain);
    }

    @Override
    public Mono<Boolean> checkMemberIdExists(Long memberId) {
        return memberRepository.checkMemberIDExists(memberId);
    }

    @Override
    public Mono<Integer> deactivateMember(ServiceParameters<CancelRequestPayload> parameters) {
        CancelRequestPayload cancelRequestPayload = parameters.getPayload().get();
        RequestHeader headers = parameters.getHeaders();
        return memberRepository.deactivateMember(cancelRequestPayload.getMemberId(),
                Long.parseLong(headers.getVisibilityScopeId()), Instant.now(), cancelRequestPayload.getUpdatedBy(),
                headers.getCorrelationId());
    }

    @Override
    public Mono<MemberProfilePayload> updateMemberProfile(
            ServiceParameters<MemberProfilePayload> parameters) {
        MemberProfilePayload memberProfileUpdated = parameters.getPayload().get();

        return memberRepository.findActiveMember(Long.parseLong(memberProfileUpdated.getId()),
                memberProfileUpdated.getVisibilityScope())
                .switchIfEmpty(Mono.error(new MemberNotFoundException(memberProfileUpdated.getCorrelationId())))
                .map(member -> {
                    memberMapper.convertDomainToEntity(memberProfileUpdated, member);
                    return member;
                })
                .flatMap(memberRepository::save)
                .switchIfEmpty(Mono.error(new MemberSaveException(memberProfileUpdated.getCorrelationId())))
                .map(memberMapper::convertEntityToDomain);
    }

    @Override
    public Mono<MemberProfilePayload> activateMemberProfile(ActivateMemberPayload activateMemberPayload) {
        return memberRepository.findMemberByEmailIdByActivationCd(activateMemberPayload.getEmailAddress())
                .switchIfEmpty(Mono.error(new MemberNotFoundException(activateMemberPayload.getCorrelationId())))
                .map(member -> {
                    member.setIsActive(Boolean.TRUE);
                    member.setActivateDate(Instant.now());
                    return member;
                })
                .flatMap(memberRepository::save)
                .switchIfEmpty(Mono.error(new MemberSaveException(activateMemberPayload.getCorrelationId())))
                .map(memberMapper::convertEntityToDomain);
    }

    @Override
    public Mono<MemberProfilePayload> createMemberProfile(
            ServiceParameters<MemberProfilePayload> parameters) {
        MemberProfilePayload memberProfileUpdated = parameters.getPayload().get();

        return Mono.just(memberProfileUpdated)
                .map(memberMapper::convertDomainToEntity)
                .flatMap(memberRepository::save)
                .switchIfEmpty(Mono.error(new MemberSaveException(memberProfileUpdated.getCorrelationId())))
                .map(memberMapper::convertEntityToDomain);
    }
}
