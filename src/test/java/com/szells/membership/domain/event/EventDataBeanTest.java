package com.szells.membership.domain.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class EventDataBeanTest {
    private EventDataBean eventDataBean;

    @Before
    public void setUp() {
        this.eventDataBean = EventDataBean.builder()
                .memberIds(new Object())
                .membershipId(12342545L)
                .events(new ArrayList<>())
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(eventDataBean);
        Assert.assertNotNull(eventDataBean.getEvents());
        Assert.assertNotNull(eventDataBean.getMemberIds());
        Assert.assertEquals(12342545L, eventDataBean.getMembershipId());
    }
}