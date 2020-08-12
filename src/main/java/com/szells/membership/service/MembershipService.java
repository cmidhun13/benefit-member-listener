package com.szells.membership.service;

import com.szells.membership.adaptor.IMembershipAdaptor;
import com.szells.membership.adaptor.IMembershipSubscriptionAdaptor;
import com.szells.membership.adaptor.IRuleEngineAdaptor;
import com.szells.membership.constants.Constants;
import com.szells.membership.model.MembershipStatus;
import com.szells.membership.model.ResourceType;
import com.szells.membership.model.ResponseStatus;
import com.szells.membership.model.payload.DeactivateMembershipPayload;
import com.szells.membership.model.payload.MembershipPayload;
import com.szells.membership.model.payload.ServiceResponse;
import com.szells.membership.model.request.MemberMembershipOnBoardRequest;
import com.szells.membership.producer.IEventPublisher;
import com.szells.util.cache.VisibilityScopeCache;
import com.szells.util.domain.RequestParameters;
import com.szells.util.domain.RequestPayload;
import com.szells.util.domain.ServiceParameters;
import com.szells.util.domain.VisibilityScopeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Function;

import static com.szells.membership.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class MembershipService implements IMembershipService {

    private final IMembershipAdaptor adaptor;
    private final IRuleEngineAdaptor ruleEngineAdaptor;
    private final IEventPublisher eventPublisher;
    private final IEventPublisher producer;
    private final IMembershipSubscriptionAdaptor membershipSubscriptionAdaptor;

    private static ServiceResponse buildSuccessResponse(MembershipPayload payload) {
        return ServiceResponse.builder()
                .correlationId(payload.getCorrelationId())
                .resourceType(ResourceType.MEMBERSHIP)
                .status(ResponseStatus.COMPLETED)
                .build();
    }

    private static Function<Long, MembershipPayload> setMembershipId(MembershipPayload payload) {
        return membershipId -> {
            payload.setId(membershipId);
            return payload;
        };
    }

    @Override
    public Mono<ServiceResponse> createMembership(ServiceParameters<MembershipPayload> serviceParams) {
        MembershipPayload payload = serviceParams.getPayload().get();
        payload.setMembershipNumber(String.valueOf(Instant.now().toEpochMilli()));
        payload.setStatus(MembershipStatus.ACTIVE.name());

        payload.setVisibilityScopeId(60L);
        VisibilityScopeCache.getInstance()
                .getCacheEntry(payload.getVisibilityScopeKey())
                .ifPresent(visibilityScope -> payload.setVisibilityScopeId(visibilityScope.getVisbilityScopeId()));

        return adaptor.createMembership(payload)
                .map(setMembershipId(payload))
                .map(this::createPoe)
                .map(MembershipService::buildSuccessResponse);
    }

    @Override
    public Mono<Integer> deactivateMembership(ServiceParameters<String> parameters) {
        return ruleEngineAdaptor.findMembershipStatusValForClient(ServiceParameters.<String>builder()
                .headers(parameters.getHeaders())
                .build())
                .map(status -> toParams(status, parameters))
                .flatMap(adaptor::deactivateMembership);
    }


    @Override
    public Mono<ServiceResponse> deactivateMembership(DeactivateMembershipPayload deactivateMembershipPayload) {
        return adaptor.getMemberShipIdByMemberIdBySolicitationId(deactivateMembershipPayload.getMemberId(), deactivateMembershipPayload.getSolicitationId())
                .map(v -> ServiceParameters.<String>builder().parameters(RequestParameters.builder().param(MEMBERSHIP_ID, v.toString()).param(MEMBERSHIP_STATUS, "False").build()).build())
                .map(v -> {
                    membershipSubscriptionAdaptor.deactivateMembership(v).subscribe();
                    return v;
                })
                .flatMap(adaptor::deactivateMembership).map(v -> ServiceResponse.builder().noOfRows(v).build());
    }

    @Override
    public Mono<ServiceResponse> activateMembership(Long memberShipID) {
        return adaptor.activateMemberShip(ServiceParameters.<String>builder().parameters(RequestParameters.builder().param(MEMBERSHIP_ID, memberShipID.toString()).param(MEMBERSHIP_STATUS, "TRUE").build()).build()).map(v -> ServiceResponse.builder().noOfRows(v).build());
    }

    @Override
    public Mono<Boolean> deactivateMembershipEvent(ServiceParameters<DeactivateMembershipPayload> payload) {
        DeactivateMembershipPayload deactivateMembershipPayload = payload.getPayload().get();
        return adaptor.getMemberShipIdByMemberIdBySolicitationId(deactivateMembershipPayload.getMemberId(), deactivateMembershipPayload.getSolicitationId())
                .filter(Objects::nonNull)
                .flatMap(v -> producer.sendEvent(payload, MEMBERSHIP_DELETE_TOPIC_NAME, DEACTIVATE_TYPE, DEACTIVATE_MEMBERSHIP));
    }

    @Override
    public Mono<Boolean> processCreateMembership(ServiceParameters<MembershipPayload> payload) {
        VisibilityScopeCache.getInstance()
                .getCacheEntry(payload.getHeaders().getVisibilityScopeKey())
                .map(VisibilityScopeData::getVisbilityScopeId)
                .ifPresent(payload.getPayload().get()::setVisibilityScopeId);

        payload.getPayload().get().setVisibilityScopeKey(payload.getHeaders().getVisibilityScopeKey());
        payload.getPayload().get().setCorrelationId(payload.getHeaders().getCorrelationId());
        return eventPublisher.sendEvent(payload, MEMBERSHIP_CREATE_TOPIC_NAME, CREATE_TYPE, CREATE_MEMBERSHIP);
    }

    @Override
    public Mono<Long> onBoardMembership(MemberMembershipOnBoardRequest payload) {
        MembershipPayload membershipPayload = new MembershipPayload();
        membershipPayload.setSolicitationId(payload.getSolicitationId());
        membershipPayload.setActive(false);
        membershipPayload.setCustomerId(payload.getCustomerId());
        return adaptor.createMembership(membershipPayload);
    }
    @Override
    public Mono<Long> onBoardMembership(MemberMembershipOnBoardRequest request, String memberNumber) {
        MembershipPayload membershipPayload = new MembershipPayload();
        membershipPayload.setSolicitationId(request.getSolicitationId());
        membershipPayload.setActive(true);
        membershipPayload.setCustomerId(request.getCustomerId());
        membershipPayload.setMembershipNumber(memberNumber);
        return adaptor.createMembership(membershipPayload);
    }

    private ServiceParameters<String> toParams(String status, ServiceParameters<String> parameters) {
        return ServiceParameters.<String>builder()
                .headers(parameters.getHeaders())
                .parameters(RequestParameters.builder()
                        .params(parameters.getParameters().getParams())
                        .param(Constants.MEMBERSHIP_STATUS, status)
                        .build())
                .build();
    }

    private MembershipPayload createPoe(MembershipPayload payload) {
        ServiceParameters<MembershipPayload> params = ServiceParameters.<MembershipPayload>builder()
                .payload(RequestPayload.<MembershipPayload>builder()
                        .payload(payload)
                        .build())
                .build();
        // rest of Membership
        // memberService.createPOE(params);
        return payload;
    }
}
