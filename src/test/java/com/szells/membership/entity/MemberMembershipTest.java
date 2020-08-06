package com.szells.membership.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class MemberMembershipTest {
    private MemberMembership memberMembership;

    @Before
    public void setUp() {
        memberMembership = new MemberMembership();
        memberMembership.setWebEnabled(true);
        memberMembership.setVisibilityScopeId(123);
        memberMembership.setReinstateReason("asdas");
        memberMembership.setMembershipMemberId(123L);
        memberMembership.setMembershipId(123L);
        memberMembership.setMemberJoinDate(Calendar.getInstance().getTime());
        memberMembership.setMemberId(123L);
        memberMembership.setIsPrimary(true);
        memberMembership.setIsActive(true);
        memberMembership.setCancelledReason("casd");
        memberMembership.setCancelledDate(Calendar.getInstance().getTime());
    }

    @Test
    public void test() {
        Assert.assertNotNull(memberMembership);
        Assert.assertNotNull(memberMembership.getReinstateReason());
        Assert.assertNotNull(memberMembership.getWebEnabled());
        Assert.assertEquals(123, memberMembership.getVisibilityScopeId());
        Assert.assertNotNull(memberMembership.getMembershipMemberId());
        Assert.assertNotNull(memberMembership.getMembershipId());
        Assert.assertNotNull(memberMembership.getMemberJoinDate());
        Assert.assertNotNull(memberMembership.getMemberId());
        Assert.assertNotNull(memberMembership.getIsPrimary());
        Assert.assertNotNull(memberMembership.getIsActive());
        Assert.assertNotNull(memberMembership.getCancelledReason());
        Assert.assertNotNull(memberMembership.getCancelledDate());


    }

}