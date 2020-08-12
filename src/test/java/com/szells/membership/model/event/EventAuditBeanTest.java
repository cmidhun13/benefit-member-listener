package com.szells.membership.model.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class EventAuditBeanTest {

    private EventAuditBean event;

    @Before
    public void setUp() {
        this.event = EventAuditBean.builder()
                .dateCreated(Calendar.getInstance().getTime())
                .dateModified(Calendar.getInstance().getTime())
                .userCreated("dasdas")
                .userModified("asdasd")
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(event);
        Assert.assertNotNull(event.getDateCreated());
        Assert.assertNotNull(event.getDateModified());
        Assert.assertNotNull(event.getUserCreated());
        Assert.assertNotNull(event.getUserModified());
    }
}