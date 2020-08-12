package com.szells.membership.service;

import com.szells.membership.model.payload.DeactivateMembershipPayload;
import com.szells.membership.model.payload.MembershipPayload;
import com.szells.membership.model.payload.ServiceResponse;
import com.szells.membership.model.request.MemberMembershipOnBoardRequest;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

public interface IMembershipService {
    Mono<ServiceResponse> createMembership(ServiceParameters<MembershipPayload> payload);

    Mono<Integer> deactivateMembership(ServiceParameters<String> parameters);

    Mono<ServiceResponse> deactivateMembership(DeactivateMembershipPayload deactivateMembershipPayload);

    Mono<ServiceResponse> activateMembership(Long memberShipID);

    Mono<Boolean> deactivateMembershipEvent(ServiceParameters<DeactivateMembershipPayload> payload);

    Mono<Boolean> processCreateMembership(ServiceParameters<MembershipPayload> payload);

    Mono<Long> onBoardMembership(MemberMembershipOnBoardRequest payload);

    Mono<Long> onBoardMembership(MemberMembershipOnBoardRequest request, String memberNumber);
}
