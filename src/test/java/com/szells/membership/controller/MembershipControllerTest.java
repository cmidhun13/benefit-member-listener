package com.szells.membership.controller;

import com.szells.membership.adaptor.IMembershipAdaptor;
import com.szells.membership.model.request.MemberSubscriptionHistory;
import com.szells.membership.model.request.ProofOfEnrollment;
import com.szells.membership.exception.InvalidMembershipIdException;
import com.szells.membership.producer.EventPublisher;
import com.szells.membership.producer.IEventPublisher;
import com.szells.util.cache.VisibilityScopeCache;
import com.szells.util.domain.VisibilityScopeData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.kafka.sender.KafkaSender;

@RunWith(SpringRunner.class)
public class MembershipControllerTest {

    private MembershipController controller;

    @Mock
    private IMembershipAdaptor membershipAdaptor;
    @Mock
    private KafkaTemplate<String, MemberSubscriptionHistory> historyKafkaTemplate;
    @Mock
    private KafkaTemplate<String, ProofOfEnrollment> poeKafkaTemplate;
    @Mock
    private KafkaSender<String, String> kafkaSender;

    @Before
    public void setup() {

        IEventPublisher eventPublisher = new EventPublisher(historyKafkaTemplate, poeKafkaTemplate, kafkaSender);

        ReflectionTestUtils.setField(eventPublisher, "membershipCreateTopic", "membership.update");
        ReflectionTestUtils.setField(eventPublisher, "bootstrapServers", "local:37234");
        /*IMemberService memberService = new MemberService(null, null, null, eventPublisher,
                new MembershipPreProcessor(), null, null, null, null,
                null, membershipAdaptor, null);*/

       // controller = new MembershipController(new MembershipService(membershipAdaptor, new RuleEngineAdaptor(), eventPublisher), memberService);


        VisibilityScopeCache.getInstance().addCacheEntry("Mc124324234", VisibilityScopeData.builder()
                .visbilityScopeId(324L)
                .isH2NGClient(true)
                .brmsTokenKey("233-3745634570834653")
                .build());
    }

    @Test
    public void testCreateMembershipSuccess() {
       /* Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(MemberServiceTest.buildKafkaResponse()));

        ResponseEntity responseEntity = controller.createMembership("Mc124324234", "23df6723",
                "Bearer 123", MembershipPayloadTest.buildPayload()).block();
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(202, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testCreateMembershipFailure() {
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.error(new Exception()));

        ResponseEntity responseEntity = controller.createMembership("Mc124324234", "23df6723",
                "Bearer 123", MembershipPayloadTest.buildPayload()).block();
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());*/
    }

    @Test
    public void testCreateMember() {
        /*Mockito.when(membershipAdaptor.checkIfMembershipExists(Mockito.any())).thenReturn(Mono.just(true));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(MemberServiceTest.buildKafkaResponse()));
        ResponseEntity response = controller.createMember("Mc12423452345", "723df2-2fj92j-23jf894", "Bearer Auth", "123434", MemberProfilePayloadTest.buildPayload()).block();
        Assert.assertNotNull(response);
        Assert.assertEquals(202, response.getStatusCodeValue());*/
    }

    @Test(expected = InvalidMembershipIdException.class)
    public void testCreateMemberFailure() {
        /*Mockito.when(membershipAdaptor.checkIfMembershipExists(Mockito.any())).thenReturn(Mono.error(new InvalidMembershipIdException("723df2-2fj92j-23jf894")));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(MemberServiceTest.buildKafkaResponse()));
        ResponseEntity response = controller.createMember("Mc12423452345", "723df2-2fj92j-23jf894", "Bearer Auth", "123434", MemberProfilePayloadTest.buildPayload()).block();*/
    }


}
