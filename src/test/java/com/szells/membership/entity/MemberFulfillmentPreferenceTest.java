package com.szells.membership.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class MemberFulfillmentPreferenceTest {

    private MemberFulfillmentPreference preference;

    @Before
    public void setUp() {
        preference = new MemberFulfillmentPreference();
        preference.setCommCategory("ajsdfh");
        preference.setCommPreferenceSource("asdeas");
        preference.setFulfillmentType("dsas");
        preference.setId(123L);
        preference.setMemberId(123L);
        preference.setMembershipId(123L);
        preference.setOptOutEnd(LocalDateTime.MAX);
        preference.setOptOutStart(LocalDateTime.MAX);
        preference.setOptOutFlag(true);
    }

    @Test
    public void test() {

        Assert.assertNotNull(preference);
        Assert.assertNotNull(preference.getCommCategory());
        Assert.assertNotNull(preference.getCommPreferenceSource());
        Assert.assertNotNull(preference.getFulfillmentType());
        Assert.assertNotNull(preference.getId());
        Assert.assertNotNull(preference.getMemberId());
        Assert.assertNotNull(preference.getMembershipId());
        Assert.assertNotNull(preference.getOptOutEnd());
        Assert.assertNotNull(preference.getOptOutStart());
        Assert.assertNotNull(preference.getOptOutFlag());

    }

}