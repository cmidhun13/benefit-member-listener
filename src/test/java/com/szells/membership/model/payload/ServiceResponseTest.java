package com.szells.membership.model.payload;

import com.szells.membership.model.ResourceType;
import com.szells.membership.model.ResponseStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServiceResponseTest {

    private ServiceResponse response;

    @Before
    public void setUp() {
        response = ServiceResponse.builder()
                .correlationId("34234")
                .failureReason("3424554")
                .noOfRows(213)
                .resourceType(ResourceType.MEMBER)
                .status(ResponseStatus.COMPLETED)
                .build();
    }

    @Test
    public void test() {
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getCorrelationId());
        Assert.assertNotNull(response.getFailureReason());
        Assert.assertNotNull(response.getNoOfRows());
        Assert.assertNotNull(response.getResourceType());
        Assert.assertNotNull(response.getStatus());
    }
}