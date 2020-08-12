package com.szells.membership.model;

import com.szells.membership.model.payload.MemberAttributeBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MemberFulfillmentPrefPayloadTest {

    private MemberFulfillmentPrefPayload payload;

    @Before
    public void setUp() {
        this.payload = MemberFulfillmentPrefPayload.builder()
                .audit(new MemberAttributeBean.AuditBean())
                .commCategory("asdasd")
                .correlationId("Asdasd")
                .fulfillmentType("asdas")
                .memberId(1223L)
                .membershipId(1233L)
                .otpOutFl(true)
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(payload);
        Assert.assertNotNull(payload.getAudit());
        Assert.assertNotNull(payload.getCommCategory());
        Assert.assertNotNull(payload.getCorrelationId());
        Assert.assertNotNull(payload.getFulfillmentType());
        Assert.assertNotNull(payload.getMemberId());
        Assert.assertNotNull(payload.getMembershipId());
        Assert.assertNotNull(payload.getOtpOutFl());
    }

}