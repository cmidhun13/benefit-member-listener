package com.szells.membership.adaptor;

import com.szells.membership.model.MembershipSubscriptionDomain;
import com.szells.membership.model.payload.CancelRequestPayload;
import com.szells.membership.model.request.EnrollRequest;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IMembershipSubscriptionAdaptor {
    Mono<Integer> cancelSubscriptions(ServiceParameters<CancelRequestPayload> parameters);

    Mono<MembershipSubscriptionDomain> createSubscriptions(ServiceParameters<MembershipSubscriptionDomain> parameters);

    Mono<Integer> deactivateMembership(ServiceParameters<String> parameters);

    Mono<List<Long>> enrollMemberMembership(EnrollRequest enrollRequest);
}
