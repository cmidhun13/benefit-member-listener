package com.szells.membership.domain.event;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportingBeanTest {

    private ReportingBean reportingBean;

    @Before
    public void setUp() {
        this.reportingBean = ReportingBean.builder()
                .catalogueName("Cata")
                .isReportingRequired(true)
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(reportingBean);
        Assert.assertNotNull(reportingBean.getCatalogueName());
        Assert.assertTrue(reportingBean.getIsReportingRequired());
    }
}