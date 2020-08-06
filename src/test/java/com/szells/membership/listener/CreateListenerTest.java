package com.szells.membership.listener;

import com.szells.membership.constants.Constants;
import com.szells.membership.domain.ResourceType;
import com.szells.membership.domain.ResponseStatus;
import com.szells.membership.domain.payload.ServiceResponse;
import com.szells.membership.service.IMemberService;
import com.szells.membership.service.IMembershipService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static com.szells.membership.constants.Constants.*;

@RunWith(SpringRunner.class)
public class CreateListenerTest {

    public static final String PAYLOAD = "{\"correlationId\":\"1342\",\"visibilityScopeKey\":\"Mc123424\",\"visibilityScopeId\":12,\"status\":\"Active\"}";
    public static final String INVALID_PAYLOAD = "[]";
    private CreateListener createListener;

    @Mock
    private IMembershipService membershipService;
    @Mock
    private IMemberService memberService;

    private static ServiceResponse buildResponse() {
        return ServiceResponse.builder().status(ResponseStatus.COMPLETED).resourceType(ResourceType.MEMBERSHIP).correlationId("asdha").build();
    }

    @Before
    public void setUp() {
        createListener = new CreateListener(membershipService, memberService);
    }

    @Test
    public void testCreateMembershipListener() {
        Mockito.when(membershipService.createMembership(Mockito.any())).thenReturn(Mono.just(buildResponse()));
        createListener.createMembershipListener(PAYLOAD, new MessageHeaders(Collections.singletonMap(Constants.CREATE_TYPE, CREATE_MEMBERSHIP.getBytes())));
        Assert.assertNotNull(createListener);
    }

    @Test
    public void testCreateMembershipListenerInvalidPayload() {
        createListener.createMembershipListener(INVALID_PAYLOAD, new MessageHeaders(Collections.singletonMap(Constants.CREATE_TYPE, CREATE_MEMBERSHIP.getBytes())));
        Assert.assertNotNull(createListener);
    }

    @Test
    public void testCreateMemberProfileListener() {
        Mockito.when(memberService.createMemberProfile(Mockito.any())).thenReturn(Mono.just(buildResponse()));
        createListener.createMembershipListener(PAYLOAD, new MessageHeaders(Collections.singletonMap(Constants.CREATE_TYPE, CREATE_MEMBER_PROFILE.getBytes())));
        Assert.assertNotNull(createListener);
    }

    @Test
    public void testCreateMemberProfileListenerInvalidPayload() {
        createListener.createMembershipListener(INVALID_PAYLOAD, new MessageHeaders(Collections.singletonMap(Constants.CREATE_TYPE, CREATE_MEMBER_PROFILE.getBytes())));
        Assert.assertNotNull(createListener);
    }

    @Test
    public void testCreateMemberProfileListenerInvalidHeader1() {
        createListener.createMembershipListener(INVALID_PAYLOAD,
                new MessageHeaders(Collections.singletonMap(Constants.UPDATE_TYPE, CREATE_MEMBER_PROFILE.getBytes())));
        Assert.assertNotNull(createListener);
    }

    @Test
    public void testCreateMemberProfileListenerInvalidHeader2() {
        createListener.createMembershipListener(INVALID_PAYLOAD,
                new MessageHeaders(Collections.singletonMap(Constants.CREATE_TYPE, UPDATE_MEMBER_PROFILE.getBytes())));
        Assert.assertNotNull(createListener);
    }

}