package com.szells.membership.model.request;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;

public class MemberSubscriptionHistoryTest {

    private MemberSubscriptionHistory history;
    private MemberSubscriptionHistory history2;

    @Before
    public void setUp() {
        AuditBean auditBean = new AuditBean();
        auditBean.setCreatedBy("23123");
        auditBean.setUpdatedBy("123123");
        auditBean.setCreatedOn(Calendar.getInstance().getTime());
        auditBean.setUpdatedOn(Calendar.getInstance().getTime());
        history = MemberSubscriptionHistory.builder()
                .correlationId("12312")
                .data(Collections.singletonMap("2312", 1232L))
                .metaData(MetaData.prepareMetaData(auditBean, "231", "12312"))
                .build();

        MetaData metaData = MetaData.builder()
                .migratedFlag(true)
                .userCase("123")
                .sourceSystem("123")
                .sourceSubSystem("benefit_history")
                .limitedDisclosure(true)
                .audit(auditBean)
                .tierChangeFromPackage("asd")
                .tierChangeFromSolicitation("12312")
                .tierChangeToPackage("aseas")
                .tierChangeToSolicitation("asdasd")
                .versionNumber("12312")
                .build();

        history2 = MemberSubscriptionHistory.builder()
                .correlationId("12312")
                .data(Collections.singletonMap("2312", 1232L))
                .metaData(metaData)
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(history);
        Assert.assertNotNull(history.getCorrelationId());
        Assert.assertNotNull(history.getData());
        Assert.assertNotNull(history.getMetaData());
        Assert.assertNotNull(history.getMetaData().getSourceSubSystem());
        Assert.assertNotNull(history.getMetaData().getUserCase());
        Assert.assertNotNull(history.getMetaData().getSourceSystem());
        Assert.assertTrue(history.getMetaData().isLimitedDisclosure());
        Assert.assertTrue(history.getMetaData().isMigratedFlag());
        Assert.assertNotNull(history.getMetaData().getAudit());
        Assert.assertNotNull(history.getMetaData().getAudit().getCreatedBy());
        Assert.assertNotNull(history.getMetaData().getAudit().getCreatedOn());
        Assert.assertNotNull(history.getMetaData().getAudit().getUpdatedBy());
        Assert.assertNotNull(history.getMetaData().getAudit().getUpdatedOn());

        Assert.assertNotNull(history2);
        Assert.assertNotNull(history2.getMetaData());
        Assert.assertNotNull(history2.getMetaData().getTierChangeFromPackage());
        Assert.assertNotNull(history2.getMetaData().getTierChangeFromSolicitation());
        Assert.assertNotNull(history2.getMetaData().getTierChangeToPackage());
        Assert.assertNotNull(history2.getMetaData().getTierChangeToSolicitation());
        Assert.assertNotNull(history2.getMetaData().getVersionNumber());

    }

}