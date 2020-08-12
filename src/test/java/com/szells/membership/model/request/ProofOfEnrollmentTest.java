package com.szells.membership.model.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;

public class ProofOfEnrollmentTest {

    private ProofOfEnrollment proofOfEnrollment;

    @Before
    public void setUp() {

        ProofOfEnrollAnswer poeAnswer = ProofOfEnrollAnswer.builder()
                .answer("12313")
                .correlationId("#24234")
                .createdBy("34242")
                .createdDate(Calendar.getInstance().getTime())
                .customQuestionText("12312")
                .encryptionAnswer("sda".getBytes())
                .encryptionKeyName("2321")
                .memberId(123L)
                .membershipId(123L)
                .poeCommonAbbrId(123)
                .questionId(123)
                .updatedBy("12312")
                .updatedDate(Calendar.getInstance().getTime())
                .proofEnrollId(123)
                .build();
        this.proofOfEnrollment = ProofOfEnrollment.builder()
                .correlationId("12312")
                .memberId(123)
                .membershipId(123)
                .organizationId(123)
                .packageId(123)
                .sourceSystem("12")
                .userCreated("12334")
                .visibilityScopeKey("21312")
                .memberProofOfEnrollmentAnswer(Collections.singletonList(poeAnswer))
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(proofOfEnrollment);
        Assert.assertNotNull(proofOfEnrollment.getCorrelationId());
        Assert.assertEquals(123, proofOfEnrollment.getMemberId());
        Assert.assertEquals(123, proofOfEnrollment.getMembershipId());
        Assert.assertEquals(123, proofOfEnrollment.getOrganizationId());
        Assert.assertEquals(123, proofOfEnrollment.getPackageId());
        Assert.assertNotNull(proofOfEnrollment.getSourceSystem());
        Assert.assertNotNull(proofOfEnrollment.getUserCreated());
        Assert.assertNotNull(proofOfEnrollment.getVisibilityScopeKey());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0));
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getAnswer());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getCorrelationId());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getCreatedBy());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getCreatedDate());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getCustomQuestionText());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getEncryptionAnswer());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getEncryptionKeyName());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getMemberId());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getMembershipId());
        Assert.assertEquals(123, proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getPoeCommonAbbrId());
        Assert.assertEquals(123, proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getProofEnrollId());
        Assert.assertEquals(123, proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getQuestionId());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getUpdatedBy());
        Assert.assertNotNull(proofOfEnrollment.getMemberProofOfEnrollmentAnswer().get(0).getUpdatedDate());
    }

}