package com.szells.membership.adaptor;

import com.szells.membership.constants.Constants;
import com.szells.membership.domain.MemberMembershipDomain;
import com.szells.membership.domain.payload.CancelRequestPayload;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.membership.exception.MemberNotFoundException;
import com.szells.membership.exception.MemberSaveException;
import com.szells.membership.mapper.IMemberMembershipMapper;
import com.szells.membership.repository.IMembersMembershipRepository;
import com.szells.util.cache.VisibilityScopeCache;
import com.szells.util.domain.ServiceParameters;
import com.szells.util.domain.VisibilityScopeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@Service
public class MemberMembershipAdaptor implements IMemberMembershipAdaptor {
    private final IMembersMembershipRepository repository;
    private final IMemberMembershipMapper mapper;

    @Override
    public Mono<MemberMembershipDomain> findActiveMembership(ServiceParameters<String> parameters) {
        return repository.findActiveMembershipByMemberId(
                Long.parseLong(parameters.getParameters().getParam(Constants.MEMBER_ID)),
                Long.parseLong(parameters.getHeaders().getVisibilityScopeId()))
                .switchIfEmpty(Mono.error(new MemberNotFoundException(parameters.getHeaders().getCorrelationId())))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<MemberMembershipDomain> createMemberMembership(ServiceParameters<MemberProfilePayload> parameters) {
        return Mono.just(parameters.getPayload().get())
                .map(mapper::buildMemberMembership)
                .flatMap(repository::save)
                .switchIfEmpty(Mono.error(new MemberSaveException(parameters.getHeaders().getCorrelationId())))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Integer> deactivateMembership(ServiceParameters<CancelRequestPayload> parameters) {
        CancelRequestPayload payload = parameters.getPayload().get();
        AtomicBoolean isH2NGClient = new AtomicBoolean(false);
        VisibilityScopeCache.getInstance()
                .getCacheEntry(parameters.getHeaders().getVisibilityScopeKey())
                .map(VisibilityScopeData::isH2NGClient)
                .ifPresent(isH2NGClient::set);
        return repository.updateMembershipStatus(payload.getMemberId(), payload.getVisibilityScopeId(), isH2NGClient.get(),
                payload.getDeleteReason(), payload.getCancelDate(), payload.getUpdatedBy(), false,
                Instant.now(), payload.getCorrelationId())
                .switchIfEmpty(Mono.error(new MemberSaveException(parameters.getHeaders().getCorrelationId())));
    }
}
