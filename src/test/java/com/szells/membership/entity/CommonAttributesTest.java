package com.szells.membership.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

public class CommonAttributesTest {
    private CommonAttributes attribute;

    @Before
    public void setUp() {
        attribute = new CommonAttributes();
        attribute.setUpdatedBy("34234");
        attribute.setCreatedBy("asdas");
        attribute.setCorrelationId("ASdasd");
        attribute.setCreatedDate(Instant.EPOCH);
        attribute.setUpdatedDate(Instant.EPOCH);
    }

    @Test
    public void test() {
        Assert.assertNotNull(attribute);
        Assert.assertNotNull(attribute.getUpdatedDate());
        Assert.assertNotNull(attribute.getUpdatedBy());
        Assert.assertNotNull(attribute.getCreatedDate());
        Assert.assertNotNull(attribute.getCreatedBy());
        Assert.assertNotNull(attribute.getCorrelationId());
    }

}