package com.szells.membership.domain.payload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MemberAttributeBeanTest {

    private MemberAttributeBean attribute;

    @Before
    public void setUp() {
        this.attribute = new MemberAttributeBean();
        attribute.setName("nam,e");
        attribute.setValue("asdhjsa");
    }

    @Test
    public void test() {
        Assert.assertNotNull(attribute);
        Assert.assertNotNull(attribute.getName());
        Assert.assertNotNull(attribute.getValue());

    }
}