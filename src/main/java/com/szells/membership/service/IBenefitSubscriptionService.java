package com.szells.membership.service;

import com.szells.membership.model.MembershipSubscriptionDomain;
import com.szells.membership.model.payload.CancelRequestPayload;
import com.szells.membership.model.payload.MemberProfilePayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBenefitSubscriptionService {
    Mono<Integer> deactivateSubscription(ServiceParameters<CancelRequestPayload> parameters);

    Mono<List<MembershipSubscriptionDomain>> createSubscriptionForMember(ServiceParameters<MemberProfilePayload> parameters);
}
