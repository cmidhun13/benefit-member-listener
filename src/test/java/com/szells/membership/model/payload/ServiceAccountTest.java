package com.szells.membership.model.payload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ServiceAccountTest {

    private ServiceAccount serviceAccount;

    @Before
    public void setUp() {
        serviceAccount = new ServiceAccount();
        serviceAccount.setAccessValidationCode("34324");
        serviceAccount.setActive(true);
        serviceAccount.setAttributes(new ArrayList<>());
        serviceAccount.setAuditBean(new MemberAttributeBean.AuditBean());
        serviceAccount.setChannel("asdas");
        serviceAccount.setCredential("asdas");
        serviceAccount.setCurrentCredential("d32d23d");
        serviceAccount.setLastAccessSuccessAt("32423");
        serviceAccount.setLastAccessValAt("asdas");
        serviceAccount.setLastAuthFailedAt("asdasd");
        serviceAccount.setNextLoginOffsetMin(2312);
        serviceAccount.setPreferences("3242");
        serviceAccount.setUserId("2312");

    }

    @Test
    public void test() {
        Assert.assertNotNull(serviceAccount);
        Assert.assertNotNull(serviceAccount.getAccessValidationCode());
        Assert.assertNotNull(serviceAccount.getAttributes());
        Assert.assertNotNull(serviceAccount.getAuditBean());
        Assert.assertNotNull(serviceAccount.getChannel());
        Assert.assertNotNull(serviceAccount.getCredential());
        Assert.assertNotNull(serviceAccount.getCurrentCredential());
        Assert.assertNotNull(serviceAccount.getLastAccessSuccessAt());
        Assert.assertNotNull(serviceAccount.getLastAccessValAt());
        Assert.assertNotNull(serviceAccount.getLastAuthFailedAt());
        Assert.assertEquals(2312, serviceAccount.getNextLoginOffsetMin());
        Assert.assertNotNull(serviceAccount.getPreferences());
        Assert.assertNotNull(serviceAccount.getUserId());
        Assert.assertTrue(serviceAccount.isActive());
    }

}