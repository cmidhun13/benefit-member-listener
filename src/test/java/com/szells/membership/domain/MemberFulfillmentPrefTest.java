package com.szells.membership.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class MemberFulfillmentPrefTest {
    private MemberFulfillmentPref pref;

    @Before
    public void setup() {
        pref = new MemberFulfillmentPref();
        pref.setCommCategory("asdasd");
        pref.setCommPreferenceSource("ASdas");
        pref.setFulfillmentType("Asdasd");
        pref.setId(123L);
        pref.setMemberId(123L);
        pref.setMembershipId(123L);
        pref.setOptOutEnd(LocalDateTime.MAX);
        pref.setOptOutStart(LocalDateTime.MAX);
        pref.setOptOutFlag(true);
    }

    @Test
    public void test() {
        Assert.assertNotNull(pref);
        Assert.assertNotNull(pref.getCommCategory());
        Assert.assertNotNull(pref.getCommPreferenceSource());
        Assert.assertNotNull(pref.getFulfillmentType());
        Assert.assertNotNull(pref.getId());
        Assert.assertNotNull(pref.getMemberId());
        Assert.assertNotNull(pref.getMembershipId());
        Assert.assertNotNull(pref.getOptOutEnd());
        Assert.assertNotNull(pref.getOptOutStart());
        Assert.assertNotNull(pref.getOptOutFlag());
    }


}