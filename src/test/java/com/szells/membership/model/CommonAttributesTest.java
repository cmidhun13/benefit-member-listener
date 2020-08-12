package com.szells.membership.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

public class CommonAttributesTest {

    private CommonAttributes attribute;
    private CommonAttributes attribute2;

    @Before
    public void setUp() {
        attribute = new CommonAttributes();
        attribute.setUpdatedBy("34234");
        attribute.setCreatedBy("asdas");
        attribute.setCorrelationId("ASdasd");
        attribute.setCreatedDate(Date.from(Instant.now()));
        attribute.setUpdatedDate(Date.from(Instant.now()));

        attribute2 = CommonAttributes.builder()
                .correlationId("233")
                .createdBy("asd")
                .updatedBy("Asd")
                .createdDate(Date.from(Instant.now()))
                .updatedDate(Date.from(Instant.now()))
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(attribute);
        Assert.assertNotNull(attribute.getUpdatedDate());
        Assert.assertNotNull(attribute.getUpdatedBy());
        Assert.assertNotNull(attribute.getCreatedDate());
        Assert.assertNotNull(attribute.getUpdatedDate());
        Assert.assertNotNull(attribute.getCorrelationId());

        Assert.assertNotNull(attribute2);
        Assert.assertNotNull(attribute2.getUpdatedDate());
        Assert.assertNotNull(attribute2.getUpdatedBy());
        Assert.assertNotNull(attribute2.getCreatedDate());
        Assert.assertNotNull(attribute2.getUpdatedDate());
        Assert.assertNotNull(attribute2.getCorrelationId());
    }

}