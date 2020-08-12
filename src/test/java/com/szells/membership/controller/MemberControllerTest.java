package com.szells.membership.controller;

//@RunWith(SpringRunner.class)
public class MemberControllerTest {
    /*private MemberController controller;

    @Mock
    private IMembershipAdaptor membershipAdaptor;
    @Mock
    private KafkaTemplate<String, MemberSubscriptionHistory> historyKafkaTemplate;
    @Mock
    private KafkaTemplate<String, ProofOfEnrollment> poeKafkaTemplate;
    @Mock
    private KafkaSender<String, String> kafkaSender;
    @Mock
    private IMemberAdaptor memberAdaptor;

    @Before
    public void setup() {
        IEventPublisher eventPublisher = new EventPublisher(historyKafkaTemplate, poeKafkaTemplate, kafkaSender);
        IMemberService memberService = new MemberService(memberAdaptor, null, null, eventPublisher,
                new MembershipPreProcessor(), null, null, null, null,
                null, membershipAdaptor, null);
        controller = new MemberController(memberService);
    }

    @Test
    public void testDeleteByMemberIdSuccess() {
        Mockito.when(memberAdaptor.findActiveMemberId(Mockito.any())).thenReturn(Mono.just(getMember()));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(MemberServiceTest.buildKafkaResponse()));
        ResponseEntity response = controller.deleteByMemberId("8263gd6823-2dj823du", "Mc12334", "1232", null, MemberServiceTest.getCancelPayload()).block();
        Assert.assertNotNull(response);
    }

    @Test(expected = MemberNotFoundException.class)
    public void testDeleteByMemberIdFailure() {
        Mockito.when(memberAdaptor.findActiveMemberId(Mockito.any())).thenReturn(Mono.empty());
        ResponseEntity response = controller.deleteByMemberId("8263gd6823-2dj823du", "Mc12334", "1232", null, MemberServiceTest.getCancelPayload()).block();
    }

    @Test
    public void testDeleteByExtrnlMemberIdSuccess() {
        Mockito.when(memberAdaptor.findMemberIdForRefNum(Mockito.any())).thenReturn(Mono.just(124L));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(MemberServiceTest.buildKafkaResponse()));
        ResponseEntity response = controller.deleteByMemberId("8263gd6823-2dj823du", "Mc12334", "1232", Constants.EXTERNAL_MEMBER_REF_NO, MemberServiceTest.getCancelPayload()).block();
        Assert.assertNotNull(response);
    }

    @Test
    public void testDeleteByExtrnlMemberIdEventFailure() {
        Mockito.when(memberAdaptor.findMemberIdForRefNum(Mockito.any())).thenReturn(Mono.just(124L));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.error(new Exception()));
        ResponseEntity response = controller.deleteByMemberId("8263gd6823-2dj823du", "Mc12334", "1232", Constants.EXTERNAL_MEMBER_REF_NO, MemberServiceTest.getCancelPayload()).block();
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatusCode());
        Assert.assertEquals(500, response.getStatusCodeValue());
    }

    @Test(expected = MemberNotFoundException.class)
    public void testDeleteByExtrnlMemberIdFailure() {
        Mockito.when(memberAdaptor.findMemberIdForRefNum(Mockito.any())).thenReturn(Mono.empty());
        ResponseEntity response = controller.deleteByMemberId("8263gd6823-2dj823du", "Mc12334", "1232", Constants.EXTERNAL_MEMBER_REF_NO, MemberServiceTest.getCancelPayload()).block();
    }


    @Test
    public void testUpdateMemberProfileSuccess() {
        Mockito.when(memberAdaptor.findActiveMemberId(Mockito.any())).thenReturn(Mono.just(getMember()));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(MemberServiceTest.buildKafkaResponse()));
        ResponseEntity response = controller.updateMemberProfile("8263gd6823-2dj823du", "Mc12334", "1232", null, MemberServiceTest.getMemberProfilePayload()).block();
        Assert.assertNotNull(response);
    }

    @Test(expected = MemberNotFoundException.class)
    public void testUpdateMemberProfileFailure() {
        Mockito.when(memberAdaptor.findActiveMemberId(Mockito.any())).thenReturn(Mono.empty());
        ResponseEntity response = controller.updateMemberProfile("8263gd6823-2dj823du", "Mc12334", "1232", null, MemberServiceTest.getMemberProfilePayload()).block();
    }

    @Test
    public void testUpdateMemberProfileByExtrnlRefSuccess() {
        Mockito.when(memberAdaptor.findActiveMemberId(Mockito.any())).thenReturn(Mono.just(getMember()));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(MemberServiceTest.buildKafkaResponse()));
        ResponseEntity response = controller.updateMemberProfile("8263gd6823-2dj823du", "Mc12334", "1232", Constants.EXTERNAL_MEMBER_REF_NO, MemberServiceTest.getMemberProfilePayload()).block();
        Assert.assertNotNull(response);
    }

    @Test(expected = MemberNotFoundException.class)
    public void testUpdateMemberProfileByExtrnlRefFailure() {
        Mockito.when(memberAdaptor.findActiveMemberId(Mockito.any())).thenReturn(Mono.empty());
        ResponseEntity response = controller.updateMemberProfile("8263gd6823-2dj823du", "Mc12334", "1232", Constants.EXTERNAL_MEMBER_REF_NO, MemberServiceTest.getMemberProfilePayload()).block();
    }

    @Test
    public void testUpdateMemberProfileEventFailure() {
        Mockito.when(memberAdaptor.findActiveMemberId(Mockito.any())).thenReturn(Mono.just(getMember()));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.error(new Exception()));
        ResponseEntity response = controller.updateMemberProfile("8263gd6823-2dj823du", "Mc12334", "1232", Constants.EXTERNAL_MEMBER_REF_NO, MemberServiceTest.getMemberProfilePayload()).block();
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatusCode());
        Assert.assertEquals(500, response.getStatusCodeValue());
    }


    private MemberDomain getMember() {
        MemberDomain memberDomain = new MemberDomain();
        memberDomain.setMemberId(1231234);
        return memberDomain;
    }
*/
}
