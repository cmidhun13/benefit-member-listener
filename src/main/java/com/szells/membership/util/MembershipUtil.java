package com.szells.membership.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szells.membership.constants.Constants;
import com.szells.membership.model.MemberFulfillmentPref;
import com.szells.membership.model.MemberFulfillmentPrefPayload;
import com.szells.membership.model.payload.*;
import com.szells.membership.model.request.*;
import com.szells.util.domain.RequestHeader;
import com.szells.util.domain.RequestPayload;
import com.szells.util.domain.ServiceParameters;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MembershipUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    private MembershipUtil() {
    }

    public static MemberFulfillmentPref buildDefaultPref(MemberFulfillmentPrefPayload payload) {

        return MemberFulfillmentPref.builder()
                .memberId(payload.getMemberId())
                .membershipId(payload.getMembershipId())
                .commCategory(payload.getCommCategory())
                .fulfillmentType(payload.getFulfillmentType())
                .correlationId(payload.getCorrelationId())
                .build();

    }

    public static Stream<MemberFulfillmentPrefPayload> buildFulfillmentPref(MemberProfilePayload payload) {
        return Stream.of(getFulfillmentPreloaded(payload, payload.isSuppressEmailFlag(), Constants.EMAIL_STR),
                getFulfillmentPreloaded(payload, payload.isSuppressSmsFlag(), Constants.SMS_STR),
                getFulfillmentPreloaded(payload, payload.isSuppressCallFlag(), Constants.PHONE_CALL_STR),
                getFulfillmentPreloaded(payload, payload.isSuppressHardLetterFlag(), Constants.HARD_LETTER_STR));
    }

    private static MemberFulfillmentPrefPayload getFulfillmentPreloaded(MemberProfilePayload payload, boolean suppressFlag, String fulfillmentType) {
        return MemberFulfillmentPrefPayload.builder()
                .correlationId(payload.getCorrelationId())
                .memberId(Long.parseLong(payload.getId()))
                .otpOutFl(suppressFlag)
                .fulfillmentType(fulfillmentType)
                .commCategory(Constants.PROMOTIONAL_STR)
                .audit(payload.getAudit())
                .build();
    }

    public static Map<String, Object> convertObjectToMap(Object payload, Long refId) {
        Map response = objectMapper.convertValue(payload, Map.class);
        response.put("referenceId", refId);
        return response;

    }

    public static List<ProofOfEnrollment> buildPOEFromMembership(MembershipPayload membership) {

        return membership.getMembers().stream()
                .filter(MembershipUtil::filterPrimaryAttributes)
                .map(member -> buildPOE(membership, member))
                .collect(Collectors.toList());
    }

    public static ProofOfEnrollment buildPOE(MemberProfilePayload member) {
        return ProofOfEnrollment.builder()
                .visibilityScopeKey(member.getVisibilityScopeKey())
                .membershipId(member.getMembershipId())
                .memberId(Long.parseLong(member.getId()))
                .userCreated(member.getAudit().getCreatedBy())
                .correlationId(member.getCorrelationId())
                .memberProofOfEnrollmentAnswer(Collections.singletonList(
                        ProofOfEnrollAnswer.builder()
                                .membershipId(member.getMembershipId())
                                .questionId(Long.parseLong(member.getSecurityQuestionId()))
                                .answer(member.getSecurityAnswer())
                                .poeCommonAbbrId(Long.parseLong(member.getPoeCommonAbbrId()))
                                .createdBy(member.getAudit().getCreatedBy())
                                .build()
                ))
                .build();
    }

    private static ProofOfEnrollment buildPOE(MembershipPayload membership, MemberProfilePayload member) {
        return ProofOfEnrollment.builder()
                .visibilityScopeKey(membership.getVisibilityScopeKey())
                .membershipId(membership.getId())
                .memberId(Long.parseLong(member.getId()))
                .userCreated(member.getAudit().getCreatedBy())
                .correlationId(membership.getCorrelationId())
                .memberProofOfEnrollmentAnswer(Collections.singletonList(
                        ProofOfEnrollAnswer.builder()
                                .membershipId(membership.getId())
                                .questionId(Long.parseLong(member.getSecurityQuestionId()))
                                .answer(member.getSecurityAnswer())
                                .poeCommonAbbrId(Long.parseLong(member.getPoeCommonAbbrId()))
                                .createdBy(member.getAudit().getCreatedBy())
                                .build()
                ))
                .build();
    }

    public static boolean filterPrimaryAttributes(MemberProfilePayload member) {
        return member.getAttributes().stream()
                .anyMatch(attribute -> attribute.getName().trim().equalsIgnoreCase(Constants.IS_PRIMARY)
                        && attribute.getValue().equalsIgnoreCase(Constants.TRUE));
    }

    public static Optional<String> generatePayloadJson(BasePayload payload) {
        String jsonPayload = null;
        try {
            jsonPayload = objectMapper.writeValueAsString(payload);
        } catch (IOException ignored) {
            //  LogMessageUtil.logMessage(LogMessageResponse.builder().msg("Error while converting payload to JSON").build(),
            //          LogLevelEnum.ERROR);
        }
        return Optional.ofNullable(jsonPayload);

    }

    public static Optional<MemberProfilePayload> getMemberProfilePayload(String payloadJson) {
        MemberProfilePayload payload = null;
        try {
            payload = objectMapper.readValue(payloadJson, MemberProfilePayload.class);
        } catch (IOException ex) {
            // LogMessageUtil.logMessage( getErrLogMessage(ex), LogLevelEnum.ERROR);
        }
        return Optional.ofNullable(payload);
    }

    public static Optional<ActivateMemberPayload> getActivateProfilePayload(String payloadJson) {
        return Optional.of(payloadJson).map(v -> {
            try {
                return objectMapper.readValue(payloadJson, ActivateMemberPayload.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    public static Optional<SuspendMemberPayload> getSuspendProfilePayload(String payloadJson) {
        return Optional.of(payloadJson).map(v -> {
            try {
                return objectMapper.readValue(payloadJson, SuspendMemberPayload.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    public static Optional<BulkUploadMember> getBulkUploadProfilePayload(String payloadJson) {
        return Optional.of(payloadJson).map(v -> {
            try {
                return objectMapper.readValue(payloadJson, BulkUploadMember.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static Optional<CreateUpdateMember> getCreateUpdateMemberPayload(String payloadJson) {
        return Optional.of(payloadJson).map(v -> {
            try {
                return objectMapper.readValue(payloadJson, CreateUpdateMember.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static Optional<UpdateHashRequest> getUpdateHashRequestPayload(String payloadJson) {
        return Optional.of(payloadJson).map(v -> {
            try {
                return objectMapper.readValue(payloadJson, UpdateHashRequest.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static Optional<MemberMembershipOnBoardRequest> getMemberMembershipOnBoardRequestPayload(String payloadJson) {
        return Optional.of(payloadJson).map(v -> {
            try {
                return objectMapper.readValue(payloadJson, MemberMembershipOnBoardRequest.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static Optional<EnrollRequest> getEnrollRequestPayload(String payloadJson) {
        return Optional.of(payloadJson).map(v -> {
            try {
                return objectMapper.readValue(payloadJson, EnrollRequest.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static Optional<MembershipPayload> getMembershipPayload(String payloadJson) {
        MembershipPayload payload = null;
        try {
            payload = objectMapper.readValue(payloadJson, MembershipPayload.class);
        } catch (IOException ex) {
            //  LogMessageUtil.logMessage( getErrLogMessage(ex), LogLevelEnum.ERROR);
        }
        return Optional.ofNullable(payload);
    }

    public static Optional<CancelRequestPayload> getCancelRequestPayload(String payloadJson) {
        CancelRequestPayload payload = null;
        try {
            payload = objectMapper.readValue(payloadJson, CancelRequestPayload.class);
        } catch (IOException ex) {
            //   LogMessageUtil.logMessage( getErrLogMessage(ex), LogLevelEnum.ERROR);
        }
        return Optional.ofNullable(payload);
    }

    public static Optional<DeactivateMembershipPayload> getDeactivateMembershipRequestPayload(String payloadJson) {
        DeactivateMembershipPayload deactivateMembershipPayload = null;
        try {
            deactivateMembershipPayload = objectMapper.readValue(payloadJson, DeactivateMembershipPayload.class);
        } catch (JsonProcessingException e) {
        }
        return Optional.ofNullable(deactivateMembershipPayload);
    }

  /*public static LogMessageResponse getErrLogMessage(Throwable ex) {
    return LogMessageResponse.builder()
            .errorDetails(ex.getMessage())
            .errorSourceSystem("benefits-membership-listener")
            .build();
  }*/


    public static ServiceParameters<MemberProfilePayload> buildMemberProfileParams(MemberProfilePayload
                                                                                           createPayload) {
        return ServiceParameters.<MemberProfilePayload>builder()
                .headers(RequestHeader.builder()
                        .correlationId(createPayload.getCorrelationId())
                        .visibilityScopeKey(createPayload.getVisibilityScopeKey())
                        .visibilityScopeId(String.valueOf(createPayload.getVisibilityScope()))
                        .build())
                .payload(RequestPayload.<MemberProfilePayload>builder()
                        .payload(createPayload)
                        .build())
                .build();
    }

    public static <T extends BasePayload> ServiceParameters<T> buildServerParamsExtendsBaseLoad(T payload) {
        return ServiceParameters.<T>builder().headers(RequestHeader.builder().correlationId(payload.getCorrelationId()).build()).payload(RequestPayload.<T>builder().payload(payload).build()).build();
    }

    public static ServiceParameters<MembershipPayload> buildMembershipParams(MembershipPayload createPayload) {
        return ServiceParameters.<MembershipPayload>builder()
                .headers(RequestHeader.builder()
                        .correlationId(createPayload.getCorrelationId())
                        .visibilityScopeKey(createPayload.getVisibilityScopeKey())
                        .visibilityScopeId(String.valueOf(createPayload.getVisibilityScopeId()))
                        .build())
                .payload(RequestPayload.<MembershipPayload>builder()
                        .payload(createPayload)
                        .build())
                .build();
    }

}
