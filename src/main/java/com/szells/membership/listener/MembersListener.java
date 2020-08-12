package com.szells.membership.listener;

import com.szells.membership.constants.Constants;
import com.szells.membership.model.ResourceType;
import com.szells.membership.model.ResponseStatus;
import com.szells.membership.model.payload.*;
import com.szells.membership.model.request.CreateUpdateMember;
import com.szells.membership.model.request.EnrollRequest;
import com.szells.membership.model.request.MemberMembershipOnBoardRequest;
import com.szells.membership.model.request.UpdateHashRequest;
import com.szells.membership.processor.IMembershipPreProcessor;
import com.szells.membership.service.IMemberService;
import com.szells.membership.service.IMembershipService;
import com.szells.membership.util.MembershipUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.szells.membership.constants.Constants.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MembersListener {

    private final IMemberService memberService;
    private final IMembershipPreProcessor preProcessor;
    private final IMembershipService membershipService;

    @KafkaListener(topics = "membership.update", containerFactory = "updateMemberListenerFactory"
            , groupId = "updateMembers")
    public void updateMembersProfile(@Payload String payload, @Headers MessageHeaders headers) {
        if (headers.containsKey(Constants.UPDATE_TYPE) &&
                Constants.UPDATE_MEMBER_PROFILE.equalsIgnoreCase(
                        new String((byte[]) Objects.requireNonNull(headers.get(Constants.UPDATE_TYPE))))) {
            MembershipUtil.getMemberProfilePayload(payload)
                    .ifPresent(this::callUpdateMembersProfile);
        }
    }

    @KafkaListener(topics = MEMBER_ACTIVATE_TOPIC_NAME, containerFactory = "activateMemberListenerFactory")
    public void activateMember(@Payload String payload, @Headers MessageHeaders headers) {
        if (headers.containsKey(Constants.UPDATE_TYPE) &&
                Constants.ACTIVATE_MEMBER.equalsIgnoreCase(
                        new String((byte[]) Objects.requireNonNull(headers.get(Constants.UPDATE_TYPE))))) {
            MembershipUtil.getActivateProfilePayload(payload)
                    .ifPresent(this::callActivateMembersProfile);
        }
    }
    @KafkaListener(topics = MEMBER_SUSPEND_TOPIC_NAME, containerFactory = "suspendMemberListenerFactory")
    public void suspendMember(@Payload String payload, @Headers MessageHeaders headers) {
        if (headers.containsKey(Constants.UPDATE_TYPE) &&
                Constants.SUSPEND_MEMBER.equalsIgnoreCase(
                        new String((byte[]) Objects.requireNonNull(headers.get(Constants.UPDATE_TYPE))))) {
            MembershipUtil.getSuspendProfilePayload(payload)
                    .ifPresent(this::callSuspendMembersProfile);
        }
    }

    @KafkaListener(topics = MEMBER_BULK_UPLOAD_TOPIC_NAME, containerFactory = "bulkUploadMemberListenerFactory")
    public void bulkUploadMember(@Payload String payload, @Headers MessageHeaders headers) {
        log.info("MembersListener.bulkupload");
        if (headers.containsKey(Constants.CREATE_TYPE) &&
                Constants.BULKUPLOAD_MEMBER.equalsIgnoreCase(
                        new String((byte[]) Objects.requireNonNull(headers.get(Constants.CREATE_TYPE))))) {
            MembershipUtil.getBulkUploadProfilePayload(payload)
                    .ifPresent(this::callBulkUploadMembersProfile);
        }
    }

    @KafkaListener(topics = MEMBER_CREATE_TOPIC_NAME, containerFactory = "createMemberListenerFactory")
    public void createMember(@Payload String payload, @Headers MessageHeaders headers) {
        log.info("MembersListener.createMember");
        if (headers.containsKey(Constants.CREATE_TYPE) &&
                Constants.CREATE_MEMBER.equalsIgnoreCase(
                        new String((byte[]) Objects.requireNonNull(headers.get(Constants.CREATE_TYPE))))) {
            MembershipUtil.getCreateUpdateMemberPayload(payload)
                    .ifPresent(this::callCreateORUpdateMember);
        } else if (headers.containsKey(Constants.CREATE_TYPE) &&
                Constants.UPDATE_MEMBER.equalsIgnoreCase(
                        new String((byte[]) Objects.requireNonNull(headers.get(Constants.CREATE_TYPE))))) {
            MembershipUtil.getUpdateHashRequestPayload(payload)
                    .ifPresent(this::callCreateOrUpdateHashCode);
        } else if (headers.containsKey(Constants.CREATE_TYPE) && Constants.ONBOARD_MEMBER_MEMBERSHIP.equals(
                new String((byte[]) Objects.requireNonNull(headers.get(Constants.CREATE_TYPE))))) {
            MembershipUtil.getMemberMembershipOnBoardRequestPayload(payload)
                    .ifPresentOrElse(this::callOnBoardMemberMembership, () -> publish(ServiceResponse.builder()
                            .status(ResponseStatus.FAILED)
                            .resourceType(ResourceType.MEMBER)
                            .build()));
        } else if (headers.containsKey(Constants.CREATE_TYPE) &&
                Constants.ENROLL_MEMBER.equalsIgnoreCase(
                        new String((byte[]) Objects.requireNonNull(headers.get(Constants.CREATE_TYPE))))) {
            MembershipUtil.getEnrollRequestPayload(payload)
                    .ifPresentOrElse(this::callEnrollMemberMembership, () -> publish(ServiceResponse.builder()
                            .status(ResponseStatus.FAILED)
                            .resourceType(ResourceType.MEMBER)
                            .build()));
        }
    }

    @KafkaListener(topics = "membership.delete", containerFactory = "deleteListenerFactory")
    public void deleteFlows(@Payload String payload, @Headers MessageHeaders headers) {
        if (headers.containsKey(Constants.DELETE_TYPE) &&
                Constants.DELETE_MEMBER_BY_ID.equalsIgnoreCase(
                        new String((byte[]) Objects.requireNonNull(headers.get(Constants.DELETE_TYPE))))) {
            MembershipUtil.getCancelRequestPayload(payload)
                    .ifPresentOrElse(this::callDeleteMemberById,
                            () -> publish(ServiceResponse.builder()
                                    .status(ResponseStatus.FAILED)
                                    .resourceType(ResourceType.MEMBER)
                                    .build()));
        } else if (headers.containsKey(Constants.DEACTIVATE_TYPE) && Constants.DEACTIVATE_MEMBERSHIP.equals(new String((byte[]) Objects.requireNonNull(headers.get(Constants.DEACTIVATE_TYPE))))) {
            MembershipUtil.getDeactivateMembershipRequestPayload(payload)
                    .ifPresentOrElse(this::callDeactivateMembershipById,
                            () -> publish(ServiceResponse.builder()
                                    .status(ResponseStatus.FAILED)
                                    .resourceType(ResourceType.MEMBER)
                                    .build()));
        }
    }

    private void callUpdateMembersProfile(MemberProfilePayload updatePayload) {
        memberService.updateMemberProfile(MembershipUtil.buildMemberProfileParams(updatePayload))
                .onErrorResume(throwable -> this.handleError(throwable, updatePayload))
                .subscribe(this::publish);
    }

    private void callActivateMembersProfile(ActivateMemberPayload activateMemberPayload) {
        memberService.activateMemberProfile(MembershipUtil.buildServerParamsExtendsBaseLoad(activateMemberPayload))
                .onErrorResume(throwable -> this.handleError(throwable, activateMemberPayload))
                .subscribe(this::publish);
    }

    private void callSuspendMembersProfile(SuspendMemberPayload suspendMemberPayload) {
        memberService.suspendMemberProfile(MembershipUtil.buildServerParamsExtendsBaseLoad(suspendMemberPayload));
    }

    private void callBulkUploadMembersProfile(BulkUploadMember bulkUploadMember) {
        memberService.bulkUploadMember(bulkUploadMember);
    }

    private void callCreateORUpdateMember(CreateUpdateMember payload) {
        memberService.createMemberAddress_1(MembershipUtil.buildServerParamsExtendsBaseLoad(payload));
    }

    private void callCreateOrUpdateHashCode(UpdateHashRequest payload) {
        memberService.updateMemberHash(MembershipUtil.buildServerParamsExtendsBaseLoad(payload));
    }

    private void callDeleteMemberById(CancelRequestPayload cancelRequestPayload) {
        memberService.deleteMemberById(preProcessor.buildCancelMemberParams(cancelRequestPayload))
                .onErrorResume(throwable -> this.handleError(throwable, cancelRequestPayload))
                .subscribe(this::publish);
    }

    private void callDeactivateMembershipById(DeactivateMembershipPayload deactivateMembershipPayload) {
        membershipService.deactivateMembership(deactivateMembershipPayload).onErrorResume(throwable -> this.handleError(throwable, deactivateMembershipPayload))
                .subscribe(this::publish);
    }

    private void callOnBoardMemberMembership(MemberMembershipOnBoardRequest request) {
        memberService.onBoardMembersSave(request);
    }

    private void callEnrollMemberMembership(EnrollRequest enrollRequest) {
        membershipService.activateMembership(enrollRequest.getMembershipId()).subscribe(this::publish);
        memberService.enrollMemberMembership(enrollRequest).map(v -> ServiceResponse.builder().build()).onErrorResume(throwable -> this.handleError(throwable, enrollRequest))
                .subscribe(this::publish);
    }

    private void publish(ServiceResponse memberProfileResponse) {
        // handle inbox
        log.info("Response :" + memberProfileResponse);
    }

    private Mono<ServiceResponse> handleError(Throwable throwable, BasePayload payload) {
        // error handling2
        log.error("Error Occurred :", throwable);
        return Mono.just(ServiceResponse.builder()
                .status(ResponseStatus.FAILED)
                .resourceType(ResourceType.MEMBER)
                .correlationId(payload.getCorrelationId())
                .failureReason(throwable.getMessage())
                .build());
    }
}
