package com.szells.membership.service;

import com.szells.membership.domain.MembershipSubscriptionDomain;
import com.szells.membership.domain.payload.CancelRequestPayload;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBenefitSubscriptionService {
    Mono<Integer> deactivateSubscription(ServiceParameters<CancelRequestPayload> parameters);

    Mono<List<MembershipSubscriptionDomain>> createSubscriptionForMember(ServiceParameters<MemberProfilePayload> parameters);
}
