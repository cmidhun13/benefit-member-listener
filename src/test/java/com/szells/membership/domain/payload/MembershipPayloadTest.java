package com.szells.membership.domain.payload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

public class MembershipPayloadTest {

    private MembershipPayload membership;

    public static MembershipPayload buildPayload() {
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
        SourceContextPayload sourceContext = new SourceContextPayload();
        sourceContext.setExternalMembershipReference("@3423");
        sourceContext.setExternalTxnDetail("asdas");
        sourceContext.setExternalTxnReference("235345");
        membership.setSourceContext(sourceContext);
        membership.setStartDate(Calendar.getInstance().getTime());
        membership.setStatus("Actove");
        membership.setTierChangeReason("32432");
        membership.setVisibilityScopeId(324L);
        membership.setVisibilityScopeKey("asdas");
        return membership;
    }

    @Before
    public void setUp() throws Exception {
        this.membership = buildPayload();
    }

    @Test
    public void test() {
        Assert.assertNotNull(membership);
        Assert.assertNotNull(membership.getAttributes());
        Assert.assertNotNull(membership.getCancelledReason());
        Assert.assertNotNull(membership.getCorrelationId());
        Assert.assertNotNull(membership.getEndDate());
        Assert.assertNotNull(membership.getLimitedDisclosureFlag());
        Assert.assertEquals(34, membership.getId());
        Assert.assertNotNull(membership.getMemberGroupType());
        Assert.assertNotNull(membership.getMembers());
        Assert.assertNotNull(membership.getMembershipGUId());
        Assert.assertNotNull(membership.getMembershipNumber());
        Assert.assertNotNull(membership.getReinstateReason());
        Assert.assertEquals(3424, membership.getRequestedTime());
        //Assert.assertEquals(121, membership.getSolicitationId());
        Assert.assertNotNull(membership.getStartDate());
        Assert.assertNotNull(membership.getStatus());
        Assert.assertNotNull(membership.getTierChangeReason());
        Assert.assertNotNull(membership.getVisibilityScopeId());
        Assert.assertEquals(324, membership.getVisibilityScopeId().longValue());
        Assert.assertNotNull(membership.getVisibilityScopeKey());
        Assert.assertNotNull(membership.getSourceContext());
        Assert.assertNotNull(membership.getSourceContext().getExternalMembershipReference());
        Assert.assertNotNull(membership.getSourceContext().getExternalTxnDetail());
        Assert.assertNotNull(membership.getSourceContext().getExternalTxnReference());
        Assert.assertTrue(membership.isActive());
        Assert.assertTrue(membership.isRuleEngineRequired());
        Assert.assertTrue(membership.isSendCorrespondenceFlag());

    }

}