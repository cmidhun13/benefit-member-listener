package com.szells.membership.util;

import com.szells.membership.constants.Constants;
import com.szells.membership.model.payload.AttributeBean;
import com.szells.membership.model.payload.MemberAttributeBean;
import com.szells.membership.model.payload.MemberProfilePayload;
import com.szells.membership.model.payload.MembershipPayload;
import com.szells.membership.model.request.ProofOfEnrollment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MembershipUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void convertObjectToMap() {
        Map<String, Object> response = MembershipUtil.convertObjectToMap(getInput(), 34L);
        Assert.assertNotNull(response);

    }

    @Test
    public void testBuildPOEForMembership() {
        List<ProofOfEnrollment> proofOfEnrollments = MembershipUtil.buildPOEFromMembership(getInput());
        Assert.assertNotNull(proofOfEnrollments);

    }


    private MembershipPayload getInput() {
        MembershipPayload membership = new MembershipPayload();
        membership.setActive(true);
        membership.setAttributes(new ArrayList<>());
        membership.setCancelledReason("asd");
        membership.setCorrelationId("Asdasd");
        membership.setEndDate(Calendar.getInstance().getTime());
        membership.setId(34);
        membership.setLimitedDisclosureFlag(true);
        membership.setMemberGroupType("acsd");
        membership.setMembers(new ArrayList<>());
        membership.setMembershipGUId("asasd");
        membership.setMembershipNumber("3432");
        membership.setReinstateReason("asdfsdf");
        membership.setRequestedTime(3424);
        membership.setRuleEngineRequired(true);
        membership.setSendCorrespondenceFlag(true);
        membership.setSolicitationId(121);
        membership.setStartDate(Calendar.getInstance().getTime());
        membership.setStatus("Actove");
        membership.setTierChangeReason("32432");
        membership.setVisibilityScopeId(324L);
        membership.setVisibilityScopeKey("asdas");
        AttributeBean attribute = new AttributeBean();
        attribute.setName(Constants.IS_PRIMARY);
        attribute.setValue(Constants.TRUE);
        attribute.setCreatedOn(Calendar.getInstance().getTime());
        attribute.setCreatedBy("Admin");
        membership.setAttributes(Collections.singletonList(attribute));
        membership.setMembers(Collections.singletonList(buildMemberProfilePayload()));
        return membership;
    }

    private MemberProfilePayload buildMemberProfilePayload() {
        MemberProfilePayload member = new MemberProfilePayload();
        member.setActive(true);
        member.setAttributes(new ArrayList<>());
        MemberAttributeBean.AuditBean audit = new MemberAttributeBean.AuditBean();
        audit.setUpdatedOn(Calendar.getInstance().getTime());
        audit.setCreatedOn(Calendar.getInstance().getTime());
        audit.setUpdatedBy("asd");
        audit.setCreatedBy("asdas");
        member.setAudit(audit);
        member.setCancelledDate(Calendar.getInstance().getTime());
        member.setCancelledReason("asdas");
        member.setCorrelationId("asdas");
        member.setDateOfBirth(Calendar.getInstance().getTime());
        member.setEmailAddressPrimary("V@ja.com");
        member.setEndDate(Calendar.getInstance().getTime());
        member.setExternalMemberReference("asdas");
        member.setFirstName("asdasd");
        member.setGender("asdas");
        member.setId("123");
        member.setIdType("asdasd");
        member.setLanguageId(3L);
        member.setLastName("asdas");
        member.setMailingName1("asdas");
        member.setMailingName2("33425");
        AttributeBean attribute = new AttributeBean();
        attribute.setName(Constants.IS_PRIMARY);
        attribute.setValue(Constants.TRUE);
        attribute.setCreatedOn(Calendar.getInstance().getTime());
        attribute.setCreatedBy("Admin");
        member.setAttributes(Collections.singletonList(attribute));

        member.setMemberGuid("234234");
        member.setMemberJoinDate(Calendar.getInstance().getTime());
        member.setMemberNumber("3424");
        member.setMiddleName("asdas");
        member.setNationalId("a235345");
        member.setPhoneNumberMobile("84275345");
        member.setPoeCommonAbbrId("342534");
        member.setPreferences("345345");
        member.setPrimary(true);
        member.setReinstateReason("sadfsdf");
        member.setSalutation("Mr");
        member.setSecurityAnswer("ASDasd");
        member.setServiceAccounts(new ArrayList<>());
        member.setStartDate(Calendar.getInstance().getTime());
        member.setStatus("Active");
        member.setSuffix("asdas");
        member.setSuppressCallFlag(true);
        member.setSuppressEmailFlag(true);
        member.setSuppressSmsFlag(true);
        member.setSuppressHardLetterFlag(true);
        member.setSecurityQuestionId("324423");
        member.setTitle("t");
        member.setVisibilityScope(12);
        member.setVisibilityScopeHash("34324");
        member.setVisibilityScopeKey("asd");
        member.setWebEnabled(true);
        return member;
    }

}