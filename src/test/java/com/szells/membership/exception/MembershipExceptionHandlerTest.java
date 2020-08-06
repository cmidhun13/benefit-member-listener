package com.szells.membership.exception;

import com.szells.util.domain.ReturnDetail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class MembershipExceptionHandlerTest {

    private MembershipExceptionHandler handler;

    @Before
    public void setUp() {
        handler = new MembershipExceptionHandler();
    }

    @Test
    public void testHandleMemberNotFoundException() {
        ResponseEntity<ReturnDetail> responseEntity = handler.handleMemberNotFoundException(new MemberNotFoundException("12312"), null);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getStatusCode());

    }

    @Test
    public void testHandleMemberSaveException() {
        ResponseEntity<ReturnDetail> responseEntity = handler.handleMemberSaveException(new MemberSaveException("12312"), null);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getStatusCode());
    }

    @Test
    public void testHandleInvalidMembershipIdException() {
        ResponseEntity<ReturnDetail> responseEntity = handler.handleInvalidMembershipIdException(new InvalidMembershipIdException("12312"), null);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getStatusCode());
    }
}