package com.szells.membership.domain.payload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class CurrentSubscriptionTest {

    private CurrentSubscription currentSubscription;

    @Before
    public void setUp() {
        currentSubscription = new CurrentSubscription();
        currentSubscription.setCancellationReason("Cascd");
        currentSubscription.setCancelledDate(Calendar.getInstance().getTime());
        currentSubscription.setEndDate(Calendar.getInstance().getTime());
        currentSubscription.setId(34235L);
        currentSubscription.setStartDate(Calendar.getInstance().getTime());
        currentSubscription.setStatus("Active");
    }

    @Test
    public void test() {
        Assert.assertNotNull(currentSubscription);
        Assert.assertNotNull(currentSubscription.getCancellationReason());
        Assert.assertNotNull(currentSubscription.getCancelledDate());
        Assert.assertNotNull(currentSubscription.getEndDate());
        Assert.assertNotNull(currentSubscription.getStartDate());
        Assert.assertNotNull(currentSubscription.getStatus());
        Assert.assertEquals(34235L, currentSubscription.getId());
    }
}