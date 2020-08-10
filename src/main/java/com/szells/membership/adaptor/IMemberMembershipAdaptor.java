package com.szells.membership.adaptor;


import com.szells.membership.domain.MemberMembershipDomain;
import com.szells.membership.domain.payload.CancelRequestPayload;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

public interface IMemberMembershipAdaptor {
    Mono<MemberMembershipDomain> findActiveMembership(ServiceParameters<String> parameters);

    Mono<MemberMembershipDomain> createMemberMembership(ServiceParameters<MemberProfilePayload> parameters);

    Mono<Integer> deactivateMembership(ServiceParameters<CancelRequestPayload> parameters);
}
