package com.szells.membership.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProofOfEnrollment {
    private final long memberId;
    private final long membershipId;
    private final List<ProofOfEnrollAnswer> memberProofOfEnrollmentAnswer;
    private final String userCreated;
    private final long packageId;
    private final long organizationId;
    private final String sourceSystem;
    private final String correlationId;
    private final String visibilityScopeKey;
}
