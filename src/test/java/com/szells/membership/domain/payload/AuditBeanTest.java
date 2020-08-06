package com.szells.membership.domain.payload;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class AuditBeanTest {
    private MemberAttributeBean.AuditBean auditBean;

    @Before
    public void setup() {
        auditBean = new MemberAttributeBean.AuditBean();
        auditBean.setCreatedBy("Created asd");
        auditBean.setUpdatedBy("ceraserer ");
        auditBean.setCreatedOn(Calendar.getInstance().getTime());
        auditBean.setUpdatedOn(Calendar.getInstance().getTime());

    }

    @Test
    public void test() {
        Assert.assertNotNull(auditBean);
        Assert.assertNotNull(auditBean.getCreatedBy());
        Assert.assertNotNull(auditBean.getCreatedOn());
        Assert.assertNotNull(auditBean.getUpdatedBy());
        Assert.assertNotNull(auditBean.getUpdatedOn());
    }
}
