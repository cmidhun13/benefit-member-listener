package com.szells.membership.domain.payload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressBeanTest {

    private AddressBean address;

    @Before
    public void setUp() {
        this.address = new AddressBean();
        address.setActive(true);
        address.setAddressLine1("Line 1");
        address.setAddressLine2("Line 2");
        address.setAddressLine3("Line 3");
        address.setCity("chennai");
        address.setCountryCode("ION");
        address.setPostalCode("545");
        address.setState("NT");
        address.setCounty("asd");
        address.setAuditBean(new MemberAttributeBean.AuditBean());
    }

    @Test
    public void test() {
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getAddressLine1());
        Assert.assertNotNull(address.getAddressLine2());
        Assert.assertNotNull(address.getAddressLine3());
        Assert.assertNotNull(address.getAuditBean());
        Assert.assertNotNull(address.getCity());
        Assert.assertNotNull(address.getCountryCode());
        Assert.assertNotNull(address.getCounty());
        Assert.assertNotNull(address.getPostalCode());
        Assert.assertNotNull(address.getPostalCode());
        Assert.assertNotNull(address.getState());
        Assert.assertTrue(address.isActive());
    }
}