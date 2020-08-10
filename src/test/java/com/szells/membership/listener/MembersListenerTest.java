package com.szells.membership.listener;

import com.szells.membership.constants.Constants;
import com.szells.membership.domain.ResourceType;
import com.szells.membership.domain.ResponseStatus;
import com.szells.membership.domain.payload.ServiceResponse;
import com.szells.membership.processor.IMembershipPreProcessor;
import com.szells.membership.service.IMemberService;
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

//@RunWith(SpringRunner.class)
public class MembersListenerTest {
/*
    private static final String PAYLOAD = "{\"correlationId\":\"1342\",\"visibilityScopeKey\":\"Mc123424\",\"visibilityScopeId\":12,\"status\":\"Active\"}";
    private static final String CANCEL_PAYLOAD = "{\"correlationId\":\"1342\",\"visibilityScopeKey\":\"Mc123424\",\"visibilityScopeId\":12,\"deleteReason\":\"Active\",\"cancelDate\":\"12-11-2020\",\"sourceSystem\":\"Helix\"}";
    private MembersListener listener;

    @Mock
    private IMembershipPreProcessor preProcessor;
    @Mock
    private IMemberService memberService;

    private static ServiceResponse buildResponse() {
        return ServiceResponse.builder().status(ResponseStatus.COMPLETED)
                .resourceType(ResourceType.MEMBERSHIP)
                .correlationId("asdha").build();
    }

    @Before
    public void setUp() {
        listener = new MembersListener(memberService, preProcessor);
    }

    @Test
    public void updateMembersProfile() {
        Mockito.when(memberService.updateMemberProfile(Mockito.any()))
                .thenReturn(Mono.just(buildResponse()));

        listener.updateMembersProfile(PAYLOAD,
                new MessageHeaders(Collections.singletonMap(Constants.UPDATE_TYPE, UPDATE_MEMBER_PROFILE.getBytes())));
        Assert.assertNotNull(listener);

    }

    @Test
    public void updateMembersProfileInvalidHeader() {
        listener.updateMembersProfile("[]",
                new MessageHeaders(Collections.singletonMap(Constants.UPDATE_TYPE, UPDATE_MEMBER_PROFILE.getBytes())));
        listener.updateMembersProfile(PAYLOAD,
                new MessageHeaders(Collections.singletonMap(Constants.UPDATE_TYPE, CREATE_MEMBERSHIP.getBytes())));
        listener.updateMembersProfile(PAYLOAD,
                new MessageHeaders(Collections.singletonMap(Constants.CREATE_TYPE, CREATE_MEMBERSHIP.getBytes())));
        Assert.assertNotNull(listener);
    }

    @Test
    public void deleteFlows() {
        Mockito.when(memberService.createMemberProfile(Mockito.any()))
                .thenReturn(Mono.just(buildResponse()));

        listener.deleteFlows(CANCEL_PAYLOAD,
                new MessageHeaders(Collections.singletonMap(Constants.DELETE_TYPE, DELETE_MEMBER_BY_ID.getBytes())));
        Assert.assertNotNull(listener);
    }

    @Test
    public void deleteFlowsInvalid() {

        listener.deleteFlows("[]",
                new MessageHeaders(Collections.singletonMap(Constants.DELETE_TYPE, DELETE_MEMBER_BY_ID.getBytes())));
        listener.deleteFlows(CANCEL_PAYLOAD,
                new MessageHeaders(Collections.singletonMap(Constants.DELETE_TYPE, UPDATE_MEMBER_PROFILE.getBytes())));
        listener.deleteFlows(CANCEL_PAYLOAD,
                new MessageHeaders(Collections.singletonMap(Constants.UPDATE_TYPE, DELETE_MEMBER_BY_ID.getBytes())));
        Assert.assertNotNull(listener);
    }*/
}