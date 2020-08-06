package com.szells.membership.service;

import com.szells.membership.domain.GenericResponse;
import com.szells.membership.domain.payload.*;
import com.szells.membership.domain.request.CreateUpdateMember;
import com.szells.membership.domain.request.EnrollRequest;
import com.szells.membership.domain.request.MemberMembershipOnBoardRequest;
import com.szells.membership.domain.request.UpdateHashRequest;
import com.szells.membership.entity.Member;
import com.szells.util.domain.ServiceParameters;
import org.apache.kafka.common.protocol.types.Field;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IMemberService {
    Mono<ServiceResponse> createMemberProfile(ServiceParameters<MemberProfilePayload> payload);

    Mono<ServiceResponse> updateMemberProfile(ServiceParameters<MemberProfilePayload> payload);

    Mono<ServiceResponse> activateMemberProfile(ServiceParameters<ActivateMemberPayload> payload);

    void suspendMemberProfile(ServiceParameters<SuspendMemberPayload> payload);

//    Mono<ServiceResponse> createMemberAddress(ServiceParameters<CreateUpdateMember> payload);

    Mono<ServiceResponse> deleteMemberById(ServiceParameters<CancelRequestPayload> payload);

    void createPOE(ServiceParameters<MembershipPayload> payload);

    Mono<Boolean> processCreateMemberEvent(ServiceParameters<MemberProfilePayload> payload);

    Mono<Boolean> processDeleteMemberById(ServiceParameters<CancelRequestPayload> payload);

    Mono<Boolean> processUpdateMembersProfile(ServiceParameters<MemberProfilePayload> payload);

    Mono<GenericResponse<Long>> processActivateMembersProfile(ServiceParameters<ActivateMemberPayload> payload);

    Mono<GenericResponse<Boolean>> processSuspendMembersProfile(ServiceParameters<SuspendMemberPayload> payload);

    Mono<GenericResponse<String>> processBulkUploadMembersProfile(ServiceParameters<BulkUploadMember> payload);

    Mono<GenericResponse<Long>> createOrUpdateMemberProfile(ServiceParameters<CreateUpdateMember> createUpdateMember);

    //    Mono<Boolean> UpdateMemberProfile(CreateUpdateMember createUpdateMember);
    void createMemberAddress_1(ServiceParameters<CreateUpdateMember> payload);

    void updateMemberHash(ServiceParameters<UpdateHashRequest> build);

    Mono<Boolean> onBoardMemberMership(ServiceParameters<MemberMembershipOnBoardRequest> parameters);

    Mono<GenericResponse<Integer>> onBoardMemberMembershipEmail(ServiceParameters<MemberMembershipOnBoardRequest> parameters);

    Mono<GenericResponse<Boolean>> updateMemberHashCode(ServiceParameters<UpdateHashRequest> updateHashRequestServiceParameters);

    void bulkUploadMember(BulkUploadMember payload);

    void onBoardMembersSave(MemberMembershipOnBoardRequest request);

    Mono<Boolean> enrollMember(ServiceParameters<EnrollRequest> build);

    Mono<List<Long>> enrollMemberMembership(EnrollRequest enrollRequest);

    GenericResponse getSolicitationList(Integer customerId,Integer solicitationId);
}
