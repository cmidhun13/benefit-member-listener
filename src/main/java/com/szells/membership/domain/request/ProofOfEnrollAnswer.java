package com.szells.membership.domain.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ProofOfEnrollAnswer {
    private final long proofEnrollId;
    private final Long memberId;
    private final Long membershipId;
    private final long questionId;
    private final String answer;
    private final long poeCommonAbbrId;
    private final String customQuestionText;
    private final String encryptionKeyName;
    private final byte[] encryptionAnswer;
    private final String createdBy;
    private final Date createdDate;
    private final String updatedBy;
    private final Date updatedDate;
    private final String correlationId;
}
