package com.szells.membership.model.payload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

public class MemberProfilePayloadTest {

    private MemberProfilePayload member;

    @Before
    public void setUp() {
        member = new MemberProfilePayload();
        member.setActive(true);
        member.setAttributes(new ArrayList<>());
        member.setAudit(new MemberAttributeBean.AuditBean());
        member.setCancelledDate(Calendar.getInstance().getTime());
        member.setCancelledReason("asdas");
        member.setCorrelationId("asdas");
        member.setCurrentSubscription(new ArrayList<>());
        member.setDateOfBirth(Calendar.getInstance().getTime());
        member.setEmailAddressPrimary("V@ja.com");
        member.setEndDate(Calendar.getInstance().getTime());
        member.setExternalMemberReference("asdas");
        member.setFirstName("asdasd");
        member.setGender("asdas");
        member.setId("asdasd");
        member.setIdType("asdasd");
        member.setLanguageId(3L);
        member.setLastName("asdas");
        member.setMailingName1("asdas");
        member.setMailingName2("33425");
        member.setMemberAttribute(new ArrayList<>());
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
        member.setPostalAddress(new AddressBean());

    }

    @Test
    public void test() {
        Assert.assertNotNull(member);
        Assert.assertNotNull(member.getAttributes());
        Assert.assertNotNull(member.getAudit());
        Assert.assertNotNull(member.getCancelledDate());
        Assert.assertNotNull(member.getCancelledReason());
        Assert.assertNotNull(member.getCorrelationId());
        Assert.assertNotNull(member.getCurrentSubscription());
        Assert.assertNotNull(member.getDateOfBirth());
        Assert.assertNotNull(member.getEmailAddressPrimary());
        Assert.assertNotNull(member.getExternalMemberReference());
        Assert.assertNotNull(member.getEndDate());
        Assert.assertNotNull(member.getFirstName());
        Assert.assertNotNull(member.getGender());
        Assert.assertNotNull(member.getId());
        Assert.assertNotNull(member.getIdType());
        Assert.assertNotNull(member.getLanguageId());
        Assert.assertNotNull(member.getLastName());
        Assert.assertNotNull(member.getMailingName1());
        Assert.assertNotNull(member.getMailingName2());
        Assert.assertNotNull(member.getMemberAttribute());
        Assert.assertNotNull(member.getMemberGuid());
        Assert.assertNotNull(member.getMemberJoinDate());
        Assert.assertNotNull(member.getMemberNumber());
        Assert.assertNotNull(member.getMiddleName());
        Assert.assertNotNull(member.getNationalId());
        Assert.assertNotNull(member.getPhoneNumberMobile());
        Assert.assertNotNull(member.getPoeCommonAbbrId());
        Assert.assertNotNull(member.getPostalAddress());
        Assert.assertNotNull(member.getPreferences());
        Assert.assertNotNull(member.getReinstateReason());
        Assert.assertNotNull(member.getSalutation());
        Assert.assertNotNull(member.getSecurityAnswer());
        Assert.assertNotNull(member.getSecurityQuestionId());
        Assert.assertNotNull(member.getServiceAccounts());
        Assert.assertNotNull(member.getStartDate());
        Assert.assertNotNull(member.getStatus());
        Assert.assertNotNull(member.getSuffix());
        Assert.assertNotNull(member.getTitle());
        Assert.assertEquals(12, member.getVisibilityScope());
        Assert.assertNotNull(member.getVisibilityScopeHash());
        Assert.assertNotNull(member.getVisibilityScopeKey());
        Assert.assertNotNull(member.getWebEnabled());
        Assert.assertNotNull(member.getAudit());
        Assert.assertTrue(member.isSuppressCallFlag());
        Assert.assertTrue(member.isSuppressEmailFlag());
        Assert.assertTrue(member.isSuppressEmailFlag());
        Assert.assertTrue(member.isSuppressSmsFlag());
        Assert.assertTrue(member.isSuppressHardLetterFlag());
        Assert.assertTrue(member.isPrimary());

    }

}