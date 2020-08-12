package com.szells.membership.service;

import com.szells.membership.model.payload.MemberProfilePayload;
import com.szells.membership.model.payload.MembershipPayload;
import com.szells.membership.model.request.MemberSubscriptionHistory;
import com.szells.membership.model.request.ProofOfEnrollment;
import com.szells.membership.mapper.IAuditBeanMapper;
import com.szells.membership.mapper.IAuditBeanMapperImpl;
import com.szells.membership.mapper.IMembershipMapper;
import com.szells.membership.mapper.IMembershipMapperImpl;
import com.szells.membership.producer.EventPublisher;
import com.szells.membership.producer.IEventPublisher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;
import reactor.kafka.sender.KafkaSender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class HistoryServiceTest {

    IAuditBeanMapper mapper;
    IEventPublisher producer;
    IMembershipMapper membershipMapper;
    private IHistoryService historyService;
    @Mock
    private KafkaTemplate<String, MemberSubscriptionHistory> historyKafkaTemplate;

    @Mock
    private KafkaTemplate<String, ProofOfEnrollment> poeKafkaTemplate;

    @Mock
    private KafkaSender<String, String> kafkaSender;

    @Before
    public void setUp() {
        mapper = new IAuditBeanMapperImpl();
        producer = new EventPublisher(historyKafkaTemplate, poeKafkaTemplate, kafkaSender);
        membershipMapper = new IMembershipMapperImpl();
        historyService = new HistoryService(mapper, producer, membershipMapper);
    }

    @Test
    public void test() {
        ListenableFuture<SendResult<String, MemberSubscriptionHistory>> response = new SettableListenableFuture<>();
//    Mockito.when(historyKafkaTemplate.send(Mockito.anyString(), Mockito.any())).thenReturn(response);
        // historyService.publishForCreateMembership(buildPayload());
        Assert.assertNotNull(historyService);
    }

    private MembershipPayload buildPayload() {
        MemberProfilePayload payload = new MemberProfilePayload();
        payload.setActive(true);
        payload.setCancelledDate(Calendar.getInstance().getTime());
        payload.setCancelledReason("Calkadskd");
        payload.setDateOfBirth(Calendar.getInstance().getTime());
        payload.setEmailAddressPrimary("v@J.com");
        payload.setEndDate(Calendar.getInstance().getTime());
        payload.setExternalMemberReference("ReferenceNumber1223");
        payload.setFirstName("first");
        payload.setGender("M");
        payload.setId("21314324");
        payload.setLanguageId(13L);
        payload.setLastName("last");
        payload.setMailingName1("line1");
        payload.setMailingName2("line2");
        payload.setMemberGuid("guid");
        payload.setMemberJoinDate(Calendar.getInstance().getTime());
        payload.setMemberNumber("23465234");
        payload.setMiddleName("middle");
        payload.setNationalId("134234");
        MembershipPayload membership = new MembershipPayload();
        membership.setActive(true);
        membership.setAttributes(new ArrayList<>());
        membership.setCancelledReason("asd");
        membership.setCorrelationId("Asdasd");
        membership.setEndDate(Calendar.getInstance().getTime());
        membership.setId(34);
        membership.setLimitedDisclosureFlag(true);
        membership.setMemberGroupType("acsd");
        membership.setMembers(Collections.singletonList(payload));
        membership.setMembershipGUId("asasd");
        membership.setMembershipNumber("3432");
        membership.setReinstateReason("asdfsdf");
        membership.setRequestedTime(3424);
        membership.setRuleEngineRequired(true);
        membership.setSendCorrespondenceFlag(true);
        membership.setSolicitationId(121);
        membership.setStartDate(Calendar.getInstance().getTime());
        membership.setStatus("Actove");
        membership.setTierChangeReason("32432");
        membership.setVisibilityScopeId(324L);
        membership.setVisibilityScopeKey("asdas");
        return membership;
    }
}