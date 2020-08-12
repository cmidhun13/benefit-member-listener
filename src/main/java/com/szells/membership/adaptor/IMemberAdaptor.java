package com.szells.membership.adaptor;

import com.szells.membership.model.MemberDomain;
import com.szells.membership.model.payload.ActivateMemberPayload;
import com.szells.membership.model.payload.CancelRequestPayload;
import com.szells.membership.model.payload.MemberProfilePayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

public interface IMemberAdaptor {
    Mono<Long> findMemberIdForRefNum(ServiceParameters<String> parameters);

    Mono<MemberProfilePayload> activateMemberProfile(ActivateMemberPayload activateMemberPayload);

    Mono<MemberProfilePayload> createMemberProfile(ServiceParameters<MemberProfilePayload> parameters);

    Mono<MemberProfilePayload> updateMemberProfile(ServiceParameters<MemberProfilePayload> parameters);

    Mono<MemberDomain> findActiveMemberId(ServiceParameters<String> parameters);

    Mono<MemberDomain> findMemberIdFromEmailAddress(ActivateMemberPayload activateMemberPayload);

    Mono<Boolean> checkMemberIdExists(Long memberId);

    Mono<Integer> deactivateMember(ServiceParameters<CancelRequestPayload> parameters);

}
