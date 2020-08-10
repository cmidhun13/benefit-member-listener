package com.szells.membership.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

public class MembershipTest {

    private Membership membership;

    @Before
    public void setUp() {
        membership = new Membership();
        membership.setExternalMembershipRef("34235");
        membership.setMembershipGUId("234534");
        membership.setMembershipId(1242345345L);
        membership.setMembershipNumber("235346356");
        membership.setCancelledReason("2345346");
        membership.setEndDate(Instant.now());
        membership.setExternalTxnDetail("23745345");
        membership.setExternalTxnRef("3252345");
        membership.setIsActive(true);
        membership.setLimitedDisclosureFlag(true);
        membership.setMemberGroupType("Group");
        membership.setReinstateReason("bhnasdyfd ");
        membership.setSolicitationRef("343253245");
        membership.setStartDate(Instant.now());
        membership.setStatus("ACtive");
        membership.setTierChangeReason("asdasdd");
        membership.setCorrelationId("435786346");
        membership.setCreatedBy("23453546");
        membership.setCreatedDate(Instant.now());
        membership.setUpdatedBy("asdasd");
        membership.setUpdatedDate(Instant.now());
    }

    @Test
    public void test() {
        Assert.assertNotNull(membership);
        Assert.assertNotNull(membership.getCancelledReason());
        Assert.assertNotNull(membership.getEndDate());
        Assert.assertNotNull(membership.getExternalMembershipRef());
        Assert.assertNotNull(membership.getExternalTxnDetail());
        Assert.assertNotNull(membership.getExternalTxnRef());
        Assert.assertNotNull(membership.getIsActive());
        Assert.assertNotNull(membership.getLimitedDisclosureFlag());
        Assert.assertNotNull(membership.getMemberGroupType());
        Assert.assertNotNull(membership.getMembershipGUId());
        Assert.assertNotNull(membership.getMembershipId());
        Assert.assertNotNull(membership.getMembershipNumber());
        Assert.assertNotNull(membership.getTierChangeReason());
        Assert.assertNotNull(membership.getStartDate());
        Assert.assertNotNull(membership.getStatus());
        Assert.assertNotNull(membership.getUpdatedBy());
        Assert.assertNotNull(membership.getUpdatedDate());
        Assert.assertNotNull(membership.getCreatedBy());
        Assert.assertNotNull(membership.getCreatedDate());
        Assert.assertNotNull(membership.getSolicitationRef());
        Assert.assertNotNull(membership.getReinstateReason());

    }
}