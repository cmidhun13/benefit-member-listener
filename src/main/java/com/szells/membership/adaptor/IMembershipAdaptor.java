package com.szells.membership.adaptor;

import com.szells.membership.model.payload.MembershipPayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

public interface IMembershipAdaptor {
    Mono<Long> createMembership(MembershipPayload membership);

    Mono<Integer> deactivateMembership(ServiceParameters<String> parameters);

    Mono<Integer> activateMemberShip(ServiceParameters<String> parameters);

    Mono<Integer> suspendMemberShip(String status, Boolean isActive, String membershipNumber,Integer customerId);

    Mono<Boolean> checkIfMembershipExists(ServiceParameters<String> parameters);

    Mono<Long> getMemberShipIdByMemberIdBySolicitationId(Long memberId, Integer solicitationId);
}
