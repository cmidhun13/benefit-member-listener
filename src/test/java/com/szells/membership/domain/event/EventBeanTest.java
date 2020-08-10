package com.szells.membership.domain.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;

public class EventBeanTest {
    EventBean eventBean;

    @Before
    public void setUp() {
        this.eventBean = EventBean.builder()
                .eventType("sdasd")
                .actionDate(Calendar.getInstance().getTime())
                .actionUser("asdasd")
                .audit(EventAuditBean.builder().build())
                .resultCode("233")
                .resultDetails("asdas")
                .sourceSystem("sdas")
                .status("sdas")
                .eventAttributes(Collections.singletonList(EventAttributeBean.builder()
                        .name("23423")
                        .value("23452345")
                        .build()))
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(eventBean);
        Assert.assertNotNull(eventBean.getActionDate());
        Assert.assertNotNull(eventBean.getActionUser());
        Assert.assertNotNull(eventBean.getAudit());
        Assert.assertNotNull(eventBean.getEventType());
        Assert.assertNotNull(eventBean.getResultCode());
        Assert.assertNotNull(eventBean.getResultDetails());
        Assert.assertNotNull(eventBean.getSourceSystem());
        Assert.assertNotNull(eventBean.getStatus());
        Assert.assertNotNull(eventBean.getEventAttributes());
        Assert.assertNotNull(eventBean.getEventAttributes().get(0));
        Assert.assertNotNull(eventBean.getEventAttributes().get(0).getName());
        Assert.assertNotNull(eventBean.getEventAttributes().get(0).getValue());
    }
}