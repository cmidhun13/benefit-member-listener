package com.szells.membership.adaptor;


import com.szells.membership.model.MemberMembershipDomain;
import com.szells.membership.model.payload.CancelRequestPayload;
import com.szells.membership.model.payload.MemberProfilePayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

public interface IMemberMembershipAdaptor {
    Mono<MemberMembershipDomain> findActiveMembership(ServiceParameters<String> parameters);

    Mono<MemberMembershipDomain> createMemberMembership(ServiceParameters<MemberProfilePayload> parameters);

    Mono<Integer> deactivateMembership(ServiceParameters<CancelRequestPayload> parameters);
}
