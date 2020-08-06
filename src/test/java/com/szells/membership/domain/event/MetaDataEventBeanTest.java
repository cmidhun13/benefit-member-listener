package com.szells.membership.domain.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MetaDataEventBeanTest {
    private MetaDataEventBean metaDataEventBean;

    @Before
    public void setUp() {
        this.metaDataEventBean = MetaDataEventBean.builder()
                .eventAuditBean(EventAuditBean.builder().build())
                .eventName("")
                .isHistory(HistoryBean.builder()
                        .catalogueName("asdasd")
                        .isHistoryRequired(true)
                        .build())
                .isReporting(ReportingBean.builder().build())
                .sourceEventPublished("asdas")
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(metaDataEventBean);
        Assert.assertNotNull(metaDataEventBean.getEventAuditBean());
        Assert.assertNotNull(metaDataEventBean.getEventName());
        Assert.assertNotNull(metaDataEventBean.getIsReporting());
        Assert.assertNotNull(metaDataEventBean.getSourceEventPublished());
        Assert.assertNotNull(metaDataEventBean.getIsHistory());
        Assert.assertNotNull(metaDataEventBean.getIsHistory().getCatalogueName());
        Assert.assertNotNull(metaDataEventBean.getIsHistory().getIsHistoryRequired());
        Assert.assertTrue(metaDataEventBean.getIsHistory().getIsHistoryRequired());
    }

}