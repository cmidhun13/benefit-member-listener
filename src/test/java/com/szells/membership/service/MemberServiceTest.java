package com.szells.membership.service;

//@RunWith(SpringRunner.class)
public class MemberServiceTest {
    /*IMemberAdaptor memberMembershipAdaptor;
    IMemberFulfillmentService fulfillmentService;
    IEventPublisher producer;
    @Mock
    IMemberRepository memberRepository;
    IMemberMapper memberMapper;
    IMemberFulfillmentPrefAdaptor adaptor;
    @Mock
    IMemberFulfillmentPreferenceRepository fulfillmentRepository;
    IFulfillmentPreferenceMapper mapper;
    @Mock
    IMembersMembershipRepository mbrMbrshpRepo;
    @Mock
    IMembershipSubscriptionRepository mbrshipSubscriptionRepo;
    private MemberService memberService;
    @Mock
    private IMemberAttributeRepository attributeRepo;
    @Mock
    private ISvcChannelAccntRepository svcChnlRepo;
    @Mock
    private IMembershipRepository membershipRepo;

    @Mock
    private KafkaTemplate<String, MemberSubscriptionHistory> historyKafkaTemplate;
    @Mock
    private KafkaTemplate<String, ProofOfEnrollment> poeKafkaTemplate;
    @Mock
    private KafkaSender<String, String> kafkaSender;
    @Mock
    private RuleEngineAdaptor ruleEngineAdaptor;

    @Mock
    private IStandingDataAdaptor standingDataAdaptor;

    public static SenderResult<Object> buildKafkaResponse() {
        RecordMetadata metadata = new RecordMetadata(null, 0, 1, 12312L, 12L, 12, 245);
        return new Response<>(metadata, null, "dn274fh4ufgh");
    }

    public static CancelRequestPayload getCancelPayload() {
        CancelRequestPayload cancelRequestPayload = new CancelRequestPayload();
        cancelRequestPayload.setCancelDate(Date.from(Instant.now()));
        //cancelRequestPayload.setCreatedBy("Admin");
        cancelRequestPayload.setDeleteReason("Test delete");
        cancelRequestPayload.setMemberId(124314L);
        cancelRequestPayload.setSourceSystem("Helix");
        cancelRequestPayload.setUpdatedBy("admin");
        cancelRequestPayload.setUpdatedDate(Date.from(Instant.now()));
        cancelRequestPayload.setCorrelationId("32r2343");
        cancelRequestPayload.setVisibilityScopeId(60L);
        cancelRequestPayload.setVisibilityScopeKey("Mc1233432");
        return cancelRequestPayload;

    }

    public static MemberProfilePayload getMemberProfilePayload() {
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
        payload.setMembershipId(213124L);

        MemberAttributeBean.AuditBean audit = new MemberAttributeBean.AuditBean();
        audit.setCreatedBy("Helix");
        audit.setCreatedOn(Date.from(Instant.now()));
        payload.setAudit(audit);
        payload.setSecurityQuestionId("12");
        payload.setSecurityAnswer("Asb");
        payload.setPoeCommonAbbrId("1232");
        return payload;
    }

    @Before
    public void setUp() {
        mapper = new IFulfillmentPreferenceMapperImpl();
        producer = new EventPublisher(historyKafkaTemplate, poeKafkaTemplate, kafkaSender);

        adaptor = new MemberFulfillmentPrefAdaptor(fulfillmentRepository, mapper);
        fulfillmentService = new MemberFulfillmentService(adaptor, mapper);

        IMemberAttributeAdaptor attributeAdaptor = new MemberAttributeAdaptor(attributeRepo);

        IMemberMembershipAdaptor mbrMembershipAdaptor = new MemberMembershipAdaptor(mbrMbrshpRepo, new IMemberMembershipMapperImpl());
        ISvcChannelAccntAdaptor svcChannelAccountAdaptor = new SvcChannelAccntAdaptor(svcChnlRepo);
        IHistoryService historyService = new HistoryService(new IAuditBeanMapperImpl(), producer, new IMembershipMapperImpl());
        IMembershipSubscriptionAdaptor benefitSubscriptionAdaptor = new MembershipSubscriptionAdaptor(mbrshipSubscriptionRepo, new IMembershipSubscriptionMapperImpl());
        IBenefitSubscriptionService benefitSubscriptionService = new BenefitSubscriptionService(benefitSubscriptionAdaptor, ruleEngineAdaptor, new IMembershipSubscriptionMapperImpl(), standingDataAdaptor);
        IMembershipAdaptor membershipAdaptor = new MembershipAdaptor(membershipRepo, new IMembershipMapperImpl());
        IMembershipService membershipService = new MembershipService(membershipAdaptor, ruleEngineAdaptor, producer);
        memberMembershipAdaptor = new MemberAdaptor(memberRepository, new IMemberMapperImpl());
        memberService = new MemberService(memberMembershipAdaptor, attributeAdaptor, fulfillmentService, producer,
                new MembershipPreProcessor(), ruleEngineAdaptor, mbrMembershipAdaptor, svcChannelAccountAdaptor, historyService,
                benefitSubscriptionService, membershipAdaptor, membershipService);
    }

    @Test
    public void testUpdateMemberProfile() {

        VisibilityScopeCache.getInstance().addCacheEntry("asdas", VisibilityScopeData.builder()
                .brmsTokenKey("asdas")
                .visbilityScopeId(60L)
                .isH2NGClient(true)
                .build());

        Mockito.when(memberRepository.findIdByExtMemberRef(Mockito.any(), Mockito.anyLong()))
                .thenReturn(Mono.just(21314324L));
        Mockito.when(memberRepository.findActiveMember(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(Mono.just(buildMember()));

        Mockito.when(memberRepository.findMemberById(Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
                .thenReturn(Mono.just(buildMember()));

        Mockito.when(memberRepository.save(Mockito.any()))
                .thenReturn(Mono.just(buildMember()));
        Mockito.when(fulfillmentRepository.findByMemberIdAndCommCategoryAndFulfillmentType(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(buildFulfillmentPref());
        Mockito.when(fulfillmentRepository.save(Mockito.any()))
                .thenReturn(buildFulfillmentPref());

        ServiceResponse response = memberService.updateMemberProfile(buildInput()).block();
        Assert.assertNotNull(response);
    }

    @Test
    public void testCreateMember() {

        VisibilityScopeCache.getInstance().addCacheEntry("asdas", VisibilityScopeData.builder()
                .brmsTokenKey("asdas")
                .visbilityScopeId(60L)
                .isH2NGClient(true)
                .build());

        Mockito.when(memberRepository.save(Mockito.any()))
                .thenReturn(Mono.just(buildMember()));
        Mockito.when(mbrMbrshpRepo.save(Mockito.any()))
                .thenReturn(Mono.just(buildMemberMbrshp()));
        Mockito.when(standingDataAdaptor.findPackagesBySolicitationId(Mockito.any()))
                .thenReturn(Flux.just(getBenefitPackageResponse()));
        Mockito.when(mbrshipSubscriptionRepo.save(Mockito.any())).thenReturn(
                Mono.just(buildSubscription()));

        ServiceResponse response = memberService.createMemberProfile(buildInput()).block();
        Assert.assertNotNull(response);
    }

    @Test
    public void testCreateMember2() {

        VisibilityScopeCache.getInstance().addCacheEntry("asdas", VisibilityScopeData.builder()
                .brmsTokenKey("asdas")
                .visbilityScopeId(60L)
                .isH2NGClient(false)
                .build());

        Mockito.when(memberRepository.save(Mockito.any()))
                .thenReturn(Mono.just(buildMember()));
        Mockito.when(mbrMbrshpRepo.save(Mockito.any()))
                .thenReturn(Mono.just(buildMemberMbrshp()));
        Mockito.when(ruleEngineAdaptor.findMembershipStatusValForClient(Mockito.any())).thenReturn(Mono.just("NEW"));
        Mockito.when(ruleEngineAdaptor.getSolicitationForClient(Mockito.any())).thenReturn(Flux.just("12312", "123344"));
        Mockito.when(mbrshipSubscriptionRepo.save(Mockito.any())).thenReturn(
                Mono.just(buildSubscription()));

        ServiceResponse response = memberService.createMemberProfile(buildInput()).block();
        Assert.assertNotNull(response);
    }

    @Test
    public void testCreateMemberFailure() {

        VisibilityScopeCache.getInstance().addCacheEntry("asdas", VisibilityScopeData.builder()
                .brmsTokenKey("asdas")
                .visbilityScopeId(60L)
                .isH2NGClient(true)
                .build());

        Mockito.when(memberRepository.save(Mockito.any()))
                .thenReturn(Mono.just(buildMember()));
        Mockito.when(mbrMbrshpRepo.save(Mockito.any()))
                .thenReturn(Mono.just(buildMemberMbrshp()));
        Mockito.when(standingDataAdaptor.findPackagesBySolicitationId(Mockito.any()))
                .thenReturn(Flux.just(getBenefitPackageResponse()));
        Mockito.when(mbrshipSubscriptionRepo.save(Mockito.any())).thenReturn(
                Mono.empty());

        ServiceResponse response = memberService.createMemberProfile(buildInput()).block();
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getFailureReason());
    }

    @Test
    public void test() {
        Assert.assertNotNull(memberRepository);
        Mockito.when(memberRepository.findIdByExtMemberRef(Mockito.any(), Mockito.anyLong()))
                .thenReturn(Mono.just(123345L));

        Long memberId = memberMembershipAdaptor.findMemberIdForRefNum(ServiceParameters.<String>builder()
                .parameters(RequestParameters.builder()
                        .param(Constants.EXTERNAL_MEMBER_REF_NO, "1235")
                        .build())
                .headers(RequestHeader.builder()
                        .visibilityScopeId("12")
                        .build())
                .build())
                .block();

        Assert.assertNotNull(memberId);
        Assert.assertEquals(123345, memberId.longValue());

    }

    @Test
    public void testDeleteMemberById() {
        Mockito.when(ruleEngineAdaptor.checkIfLightFamiliesActivated(Mockito.any())).thenReturn(Mono.just(false));
        Mockito.when(memberRepository.findMemberById(Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean())).thenReturn(Mono.just(buildMember()));
        Mockito.when(attributeRepo.updateStatusForMemberId(Mockito.any(), Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Mono.just(2));
        Mockito.when(mbrMbrshpRepo.updateMembershipStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(1));
        Mockito.when(svcChnlRepo.updateStatusForMemberId(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(1));
        Mockito.when(ruleEngineAdaptor.findMembershipStatusValForClient(Mockito.any())).thenReturn(Mono.just("Cancelled"));
        Mockito.when(mbrshipSubscriptionRepo.deactivateSubscriptionStatusForMember(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(1));
        Mockito.when(memberRepository.deactivateMember(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(1));

        ServiceResponse serviceResponse = memberService.deleteMemberById(buildCancelPaload()).block();
        Assert.assertNotNull(serviceResponse);
        Assert.assertNotNull(serviceResponse.getNoOfRows());
    }

    @Test
    public void testDeleteMembershipById() {
        Mockito.when(ruleEngineAdaptor.checkIfLightFamiliesActivated(Mockito.any())).thenReturn(Mono.just(true));
        Mockito.when(mbrMbrshpRepo.findActiveMembershipByMemberId(Mockito.any(), Mockito.any())).thenReturn(Mono.just(buildMembership()));
        Mockito.when(membershipRepo.updateMembershipStatus(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(1));

        Mockito.when(memberRepository.findMemberById(Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean())).thenReturn(Mono.just(buildMember()));
        Mockito.when(attributeRepo.updateStatusForMemberId(Mockito.any(), Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Mono.just(2));
        Mockito.when(mbrMbrshpRepo.updateMembershipStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(1));
        Mockito.when(svcChnlRepo.updateStatusForMemberId(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(1));
        Mockito.when(ruleEngineAdaptor.findMembershipStatusValForClient(Mockito.any())).thenReturn(Mono.just("Cancelled"));
        Mockito.when(mbrshipSubscriptionRepo.deactivateSubscriptionStatusForMember(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(1));
        Mockito.when(memberRepository.deactivateMember(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(1));

        ServiceResponse serviceResponse = memberService.deleteMemberById(buildCancelPaload()).block();
        Assert.assertNotNull(serviceResponse);
        Assert.assertNotNull(serviceResponse.getNoOfRows());
    }

    @Test
    public void testDeleteMembershipByIdNotPresent() {
        Mockito.when(ruleEngineAdaptor.checkIfLightFamiliesActivated(Mockito.any())).thenReturn(Mono.just(true));
        Mockito.when(mbrMbrshpRepo.findActiveMembershipByMemberId(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());

        Mockito.when(memberRepository.findMemberById(Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean())).thenReturn(Mono.just(buildMember()));
        Mockito.when(attributeRepo.updateStatusForMemberId(Mockito.any(), Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Mono.just(2));
        Mockito.when(mbrMbrshpRepo.updateMembershipStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(1));
        Mockito.when(svcChnlRepo.updateStatusForMemberId(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(1));
        Mockito.when(ruleEngineAdaptor.findMembershipStatusValForClient(Mockito.any())).thenReturn(Mono.just("Cancelled"));
        Mockito.when(mbrshipSubscriptionRepo.deactivateSubscriptionStatusForMember(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Mono.just(1));
        Mockito.when(memberRepository.deactivateMember(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(1));

        ServiceResponse serviceResponse = memberService.deleteMemberById(buildCancelPaload()).block();
        Assert.assertNotNull(serviceResponse);
        Assert.assertNotNull(serviceResponse.getNoOfRows());
    }

    @Test
    public void testProcessDeleteMemberById() {
        Mockito.when(memberRepository.findIdByExtMemberRef(Mockito.any(), Mockito.anyLong()))
                .thenReturn(Mono.just(123345L));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(buildKafkaResponse()));
        Boolean deleteCompleted = memberService.processDeleteMemberById(buildCancelPaload()).block();
        Assert.assertNotNull(deleteCompleted);
        Assert.assertTrue(deleteCompleted);

    }

    @Test
    public void testProcessUpdateMembersProfile() {
        Mockito.when(memberRepository.findMemberById(Mockito.any(), Mockito.anyLong(), Mockito.any()))
                .thenReturn(Mono.just(buildMember()));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(buildKafkaResponse()));
        Boolean updateMemberCompleted = memberService.processUpdateMembersProfile(buildInput()).block();
        Assert.assertNotNull(updateMemberCompleted);
        Assert.assertTrue(updateMemberCompleted);

    }

    @Test
    public void testProcessCreateMemberEvent() {
        Mockito.when(membershipRepo.findById(Mockito.anyLong())).thenReturn(Mono.just(buildMbrshp()));
        Mockito.when(kafkaSender.send(Mockito.any())).thenReturn(Flux.just(buildKafkaResponse()));
        Boolean updateMemberCompleted = memberService.processCreateMemberEvent(buildInput()).block();
        Assert.assertNotNull(updateMemberCompleted);
        Assert.assertTrue(updateMemberCompleted);

    }

    private MemberMembership buildMembership() {
        MemberMembership memberMembership = new MemberMembership();
        memberMembership.setMembershipMemberId(213461L);
        memberMembership.setMembershipId(213L);
        memberMembership.setMemberId(123L);
        return memberMembership;
    }

    private Membership buildMbrshp() {
        Membership membership = new Membership();
        membership.setMembershipId(12L);
        return membership;
    }

    private MemberMembership buildMemberMbrshp() {
        MemberMembership memberMembership = new MemberMembership();
        memberMembership.setWebEnabled(true);
        memberMembership.setVisibilityScopeId(123);
        memberMembership.setReinstateReason("asdas");
        memberMembership.setMembershipMemberId(123L);
        memberMembership.setMembershipId(123L);
        memberMembership.setMemberJoinDate(Calendar.getInstance().getTime());
        memberMembership.setMemberId(123L);
        memberMembership.setIsPrimary(true);
        memberMembership.setIsActive(true);
        memberMembership.setCancelledReason("casd");
        memberMembership.setCancelledDate(Calendar.getInstance().getTime());
        return memberMembership;
    }

    private MembershipBenefitSubscription buildSubscription() {
        MembershipBenefitSubscription subscription = new MembershipBenefitSubscription();
        subscription.setIsActive(true);
        subscription.setBenefitKey("21312");
        subscription.setBenefitRef(null);
        subscription.setVisibilityScopeId(23L);
        subscription.setStatus("Active");
        subscription.setCancelledReason(null);
        subscription.setCancelledDate(null);
        subscription.setEndDate(null);
        subscription.setId(123L);
        subscription.setMemberId(1234L);
        subscription.setMembershipId(213L);
        subscription.setStartDate(Instant.now());
        subscription.setCorrelationId("12342");
        subscription.setCreatedBy("HJel");
        subscription.setCreatedDate(Instant.EPOCH);
        return subscription;
    }

    private BenefitPackageResponse getBenefitPackageResponse() {
        BenefitPackageResponse packageResponse = new BenefitPackageResponse();
        packageResponse.setActiveFlag("true");
        packageResponse.setBenefitId(1);
        packageResponse.setBenefitPackageId(1);
        packageResponse.setDisplaySeq(1);
        packageResponse.setFlexibleFlag("true");
        packageResponse.setIsDefaultFlexibleBenefit(2);
        packageResponse.setPrimaryOnly(1);
        packageResponse.setUserPaysFlag("true");

        return packageResponse;
    }

    private ServiceParameters<CancelRequestPayload> buildCancelPaload() {
        return ServiceParameters.<CancelRequestPayload>builder()
                .headers(RequestHeader.builder()
                        .visibilityScopeKey("Mc1233432")
                        .visibilityScopeId("60")
                        .correlationId("hf822fj0324f-2fj924f24f")
                        .brmsTokenKey("1343-2fi324f4j")
                        .authorization("Bearer dh248hf84hf384fgh347frh")
                        .build())
                .parameters(RequestParameters.builder()
                        .param(Constants.ID_TYPE, Constants.EXTERNAL_MEMBER_REF_NO)
                        .param(Constants.MEMBER_ID, "124314")
                        .build())
                .payload(RequestPayload.<CancelRequestPayload>builder()
                        .payload(getCancelPayload())
                        .build())
                .build();
    }

    private Mono<MemberFulfillmentPreference> buildFulfillmentPref() {
        MemberFulfillmentPreference preference = new MemberFulfillmentPreference();
        preference.setCommCategory("ajsdfh");
        preference.setCommPreferenceSource("asdeas");
        preference.setFulfillmentType("dsas");
        preference.setId(123L);
        preference.setMemberId(123L);
        preference.setMembershipId(123L);
        preference.setOptOutEnd(LocalDateTime.MAX);
        preference.setOptOutStart(LocalDateTime.MAX);
        preference.setOptOutFlag(true);
        return Mono.just(preference);
    }

    private Member buildMember() {
        Member member = new Member();
        member.setAofKey("123");
        //member.setDob(Calendar.getInstance().getTime());
        member.setEmailId("Casd");
        member.setExtMemberRef("asdasd");
        member.setGender("M");
        member.setIsActive(true);
       // member.setEndDate(Calendar.getInstance().getTime());
        //member.setStartDate(Calendar.getInstance().getTime());
        member.setMailingName1("asda");
        member.setMailingName2("Asd");
        member.setLastName("asd");
        member.setMiddleName("asdas");
        member.setLanguageId(123L);
        member.setFirstName("asdas");
        member.setMemberGUId("sadasd");
        member.setNationalId("3342345");
        member.setMemberNumber("231231234");
        member.setPhoneNumber("2334");
        member.setPreferences("314234");
        member.setSalutation("Mr");
        member.setSuffix("asd");
        member.setTitle("Asdas");
        member.setVisibilityScopeId(123L);
        //member.setMemberId(21314324);
        return member;
    }

    private ServiceParameters<MemberProfilePayload> buildInput() {
        MemberProfilePayload payload = getMemberProfilePayload();

        return ServiceParameters.<MemberProfilePayload>builder()
                .payload(RequestPayload.<MemberProfilePayload>builder()
                        .payload(payload)
                        .build())
                .parameters(RequestParameters.builder()
                        .param(Constants.MEMBERSHIP_ID, "124314")
                        .param(Constants.MEMBER_ID, "124314")
                        .build())
                .headers(RequestHeader.builder()
                        .visibilityScopeId("60")
                        .visibilityScopeKey("asdas")
                        .correlationId("asdas")
                        .authorization("asdas")
                        .brmsTokenKey("asdas")
                        .build())
                .build();
    }

    static class Response<T> implements SenderResult<T> {
        private final RecordMetadata metadata;
        private final Exception exception;
        private final T correlationMetadata;

        public Response(RecordMetadata metadata, Exception exception, T correlationMetadata) {
            this.metadata = metadata;
            this.exception = exception;
            this.correlationMetadata = correlationMetadata;
        }

        @Override
        public RecordMetadata recordMetadata() {
            return metadata;
        }

        @Override
        public Exception exception() {
            return exception;
        }

        @Override
        public T correlationMetadata() {
            return correlationMetadata;
        }

        @Override
        public String toString() {
            return String.format("Correlation=%s metadata=%s exception=%s", correlationMetadata, metadata, exception);
        }
    }*/
}