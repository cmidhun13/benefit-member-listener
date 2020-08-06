package com.szells.membership.domain.payload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class AttributeBeanTest {

    private AttributeBean attribute;

    @Before
    public void setUp() {
        this.attribute = new AttributeBean();
        attribute.setCreatedBy("");
        attribute.setCreatedOn(Calendar.getInstance().getTime());
        attribute.setName("JNMa");
        attribute.setValue("asdsad");
    }

    @Test
    public void test() {
        Assert.assertNotNull(attribute);
        Assert.assertNotNull(attribute.getCreatedBy());
        Assert.assertNotNull(attribute.getCreatedOn());
        Assert.assertNotNull(attribute.getName());
        Assert.assertNotNull(attribute.getValue());

    }

}