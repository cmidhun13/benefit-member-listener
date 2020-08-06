package com.szells.membership.service;

import com.szells.membership.adaptor.*;
import com.szells.membership.domain.payload.MembershipPayload;
import com.szells.membership.domain.payload.ServiceResponse;
import com.szells.membership.entity.Membership;
import com.szells.membership.mapper.*;
import com.szells.membership.processor.IMembershipPreProcessor;
import com.szells.membership.producer.IEventPublisher;
import com.szells.membership.repository.IMemberFulfillmentPreferenceRepository;
import com.szells.membership.repository.IMemberRepository;
import com.szells.membership.repository.IMembershipRepository;
import com.szells.util.cache.VisibilityScopeCache;
import com.szells.util.domain.RequestHeader;
import com.szells.util.domain.RequestPayload;
import com.szells.util.domain.ServiceParameters;
import com.szells.util.domain.VisibilityScopeData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

//@RunWith(SpringRunner.class)
public class MembershipServiceTest {
    /*IMembershipAdaptor adaptor;
    IMemberService memberService;
    @Mock
    IMemberRepository memberRepository;
    @Mock
    IMemberFulfillmentPreferenceRepository fulfillmentRepository;
    @Mock
    IMembershipRepository membershipRepository;
    private IMembershipService service;
    @Mock
    private IMemberAdaptor memberAdaptor;
    @Mock
    private IMemberAttributeAdaptor attributeAdaptor;
    @Mock
    private IMemberFulfillmentService fulfillmentService;
    @Mock
    private IEventPublisher producer;
    @Mock
    private IMembershipPreProcessor preProcessor;
    @Mock
    private IRuleEngineAdaptor ruleEngineAdaptor;
    @Mock
    private IMemberMembershipAdaptor mbrMembershipAdaptor;
    @Mock
    private ISvcChannelAccntAdaptor svcChannelAccountAdaptor;
    @Mock
    private IHistoryService historyService;
    @Mock
    private IBenefitSubscriptionService benefitSubscriptionService;
    @Mock
    private IMembershipAdaptor membershipAdaptor;
    @Mock
    private IMembershipService membershipService;

    @Before
    public void setup() {

        IMemberMapper memberMapper = new IMemberMapperImpl();
        IFulfillmentPreferenceMapper mapper = new IFulfillmentPreferenceMapperImpl();
        IMemberAdaptor memberMembershipAdaptor = new MemberAdaptor(memberRepository, memberMapper);
        IMemberFulfillmentPrefAdaptor adaptor = new MemberFulfillmentPrefAdaptor(fulfillmentRepository, mapper);
        IMemberFulfillmentService fulfillmentService = new MemberFulfillmentService(adaptor, mapper);
        memberService = new MemberService(memberAdaptor, attributeAdaptor, fulfillmentService, producer,
                preProcessor, ruleEngineAdaptor, mbrMembershipAdaptor, svcChannelAccountAdaptor, historyService,
                benefitSubscriptionService, membershipAdaptor, membershipService);
        IMembershipAdaptor membershipAdaptor = new MembershipAdaptor(membershipRepository, new IMembershipMapperImpl());
        service = new MembershipService(membershipAdaptor, ruleEngineAdaptor, producer);
        VisibilityScopeCache.getInstance().addCacheEntry("asdas", VisibilityScopeData.builder()
                .visbilityScopeId(60L)
                .clientId(34234L)
                .brmsTokenKey("asdas")
                .build());
    }

    @Test
    public void testCreateMembership() {
        Mockito.when(membershipRepository.save(Mockito.any()))
                .thenReturn(Mono.just(buildMembership()));
        ServiceResponse response = service.createMembership(buildInput()).block();
        Assert.assertNotNull(response);
    }

    private Membership buildMembership() {
        Membership membership = new Membership();
        membership.setExternalMembershipRef("34235");
        membership.setMembershipGUId("234534");
        membership.setMembershipId(1242345345L);
        membership.setMembershipNumber("235346356");
        membership.setCancelledReason("2345346");
        membership.setEndDate(Instant.now());
        membership.setExternalTxnDetail("23745345");
        membership.setExternalTxnRef("3252345");
        membership.setIsActive(true);
        membership.setLimitedDisclosureFlag(true);
        membership.setMemberGroupType("Group");
        membership.setReinstateReason("bhnasdyfd ");
        membership.setSolicitationRef("343253245");
        membership.setStartDate(Instant.now());
        membership.setStatus("ACtive");
        membership.setTierChangeReason("asdasdd");
        membership.setCorrelationId("435786346");
        membership.setCreatedBy("23453546");
        membership.setCreatedDate(Instant.now());
        membership.setUpdatedBy("asdasd");
        membership.setUpdatedDate(Instant.now());
        return membership;
    }

    private ServiceParameters<MembershipPayload> buildInput() {
        MembershipPayload membership = new MembershipPayload();
        membership.setActive(true);
        membership.setAttributes(new ArrayList<>());
        membership.setCancelledReason("asd");
        membership.setCorrelationId("Asdasd");
        membership.setEndDate(Calendar.getInstance().getTime());
        membership.setId(34);
        membership.setLimitedDisclosureFlag(true);
        membership.setMemberGroupType("acsd");
        membership.setMembers(new ArrayList<>());
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
        return ServiceParameters.<MembershipPayload>builder()
                .headers(RequestHeader.builder()
                        .visibilityScopeId("50")
                        .correlationId("asdas")
                        .visibilityScopeKey("MC13424324")
                        .build())
                .payload(RequestPayload.<MembershipPayload>builder()
                        .payload(membership)
                        .build())
                .build();
    }
*/
}
