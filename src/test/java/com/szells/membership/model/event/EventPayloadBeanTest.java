package com.szells.membership.model.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EventPayloadBeanTest {

    private EventPayloadBean eventPayloadBean;

    @Before
    public void setUp() {
        eventPayloadBean = EventPayloadBean.builder()
                .correlationId("32435")
                .data(EventDataBean.builder().build())
                .metaData(MetaDataEventBean.builder().build())
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(eventPayloadBean);
        Assert.assertNotNull(eventPayloadBean.getCorrelationId());
        Assert.assertNotNull(eventPayloadBean.getData());
        Assert.assertNotNull(eventPayloadBean.getMetaData());
    }

}