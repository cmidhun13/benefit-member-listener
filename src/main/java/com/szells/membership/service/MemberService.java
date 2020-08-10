package com.szells.membership.service;

import com.szells.membership.adaptor.*;
import com.szells.membership.constants.Constants;
import com.szells.membership.domain.*;
import com.szells.membership.domain.payload.*;
import com.szells.membership.domain.request.*;
import com.szells.membership.entity.*;
import com.szells.membership.exception.InvalidMemberIdException;
import com.szells.membership.exception.MemberNotFoundException;
import com.szells.membership.exception.MemberSaveException;
import com.szells.membership.mail.MessageSender;
import com.szells.membership.processor.IMembershipPreProcessor;
import com.szells.membership.producer.IEventPublisher;
import com.szells.membership.repository.*;
import com.szells.membership.util.MembershipUtil;
import com.szells.util.domain.RequestParameters;
import com.szells.util.domain.RequestPayload;
import com.szells.util.domain.ServiceParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.szells.membership.constants.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements IMemberService {

    private final IMemberAdaptor memberAdaptor;
    private final IMemberAttributeAdaptor attributeAdaptor;
    @Lazy
    private final IMemberFulfillmentService fulfillmentService;
    private final IEventPublisher producer;
    private final IMembershipPreProcessor preProcessor;
    private final IRuleEngineAdaptor ruleEngineAdaptor;
    private final IMemberMembershipAdaptor memberMembershipAdaptor;
    private final ISvcChannelAccntAdaptor svcChannelAccountAdaptor;
    @Lazy
    private final IHistoryService historyService;
    @Lazy
    private final IBenefitSubscriptionService benefitSubscriptionService;
    private final IMembershipAdaptor membershipAdaptor;
    @Lazy
    private final IMembershipService membershipService;
    private final IMemberAddressRepository memberAddressRepository;
    private final IMemberRepository memberRepository;
    private final IMembershipRepository membershipRepository;
    private final IMemberUserRepository memberUserRepository;
    private final ILoginActivityRepository loginActivityRepository;
    private final IMembershipSubscriptionAdaptor membershipSubscriptionAdaptor;
    private final IBenPkgSelectionMappingAdoptor benPkgSelectionMappingAdoptor;
    private final MessageSender messageSender;
    private final CustomerService customerService;

    @Value("${storage.location}")
    private String rootPath;
    private static ServiceParameters<MemberProfilePayload> buildUpdateProfilePayload
            (MemberProfilePayload payload) {
        return ServiceParameters.<MemberProfilePayload>builder()
                .payload(RequestPayload.<MemberProfilePayload>builder()
                        .payload(payload).build())
                .build();

    }

    private static Function<Long, MemberProfilePayload> setMemberIdToPayload(MemberProfilePayload memberProfile) {
        return memberId -> {
            memberProfile.setId(memberId.toString());
            return memberProfile;
        };
    }

    private static ServiceParameters<String> setMembershipId(ServiceParameters<String> parameters, Long membershipId) {
        return ServiceParameters.<String>builder()
                .headers(parameters.getHeaders())
                .payload(parameters.getPayload())
                .parameters(RequestParameters.builder()
                        .params(parameters.getParameters().getParams())
                        .param(Constants.MEMBERSHIP_ID, String.valueOf(membershipId))
                        .build())
                .build();
    }

    @Override
    public Mono<ServiceResponse> createMemberProfile(ServiceParameters<MemberProfilePayload> payload) {
        return memberAdaptor.createMemberProfile(payload)
                .flatMap(memberProfile -> memberMembershipAdaptor.createMemberMembership(payload))
                .flatMap(membership -> benefitSubscriptionService.createSubscriptionForMember(payload))
                .map(subscriptions -> {
                    createPOEForMember(payload);
                    return subscriptions;
                })
                .map(subscriptions -> ServiceResponse.builder()
                        .correlationId(payload.getHeaders().getCorrelationId())
                        .resourceType(ResourceType.MEMBER)
                        .status(ResponseStatus.COMPLETED)
                        .noOfRows(1)
                        .build())
                .onErrorResume(throwable -> Mono.just(ServiceResponse.builder()
                        .correlationId(payload.getHeaders().getCorrelationId())
                        .resourceType(ResourceType.MEMBER)
                        .failureReason(throwable.getMessage())
                        .status(ResponseStatus.FAILED)
                        .build()));
    }

    @Override
    public Mono<ServiceResponse> updateMemberProfile(ServiceParameters<MemberProfilePayload> payload) {

        MemberProfilePayload memberProfile = payload.getPayload().get();

        return fetchMemberId(memberProfile.getIdType(), memberProfile.getId(), payload)
                .switchIfEmpty(Mono.error(new InvalidMemberIdException(memberProfile.getCorrelationId())))
                .map(setMemberIdToPayload(memberProfile))
                .map(MemberService::buildUpdateProfilePayload)
                .flatMap(memberAdaptor::updateMemberProfile)
                .switchIfEmpty(Mono.error(new MemberSaveException(memberProfile.getCorrelationId())))
                .flatMap(mbrProfile -> updateFulfillment(memberProfile));
    }

    @Override
    public Mono<ServiceResponse> activateMemberProfile(ServiceParameters<ActivateMemberPayload> payload) {
        ActivateMemberPayload activateMemberPayload = payload.getPayload().get();
        return memberAdaptor.activateMemberProfile(activateMemberPayload)
                .flatMap(v -> updateLoginActivity(v.getMembershipId(), "Activated the Profile"))
                .switchIfEmpty(Mono.error(new MemberSaveException(activateMemberPayload.getCorrelationId())))
                .map(v -> ServiceResponse.builder()
                        .correlationId(activateMemberPayload.getCorrelationId())
                        .resourceType(ResourceType.MEMBER)
                        .status(ResponseStatus.COMPLETED)
                        .noOfRows(1)
                        .build());
    }
    @Override
    public void suspendMemberProfile(ServiceParameters<SuspendMemberPayload> payload) {
        SuspendMemberPayload suspendMemberPayload = payload.getPayload().get();
        String status = suspendMemberPayload.getStatus();
        Boolean isActive = (SUSPEND.equals(status))?false:true;
        Flux.fromIterable(suspendMemberPayload.getMemberId()).flatMap(memberId ->
                memberRepository.findMemberByMemberID(Long.parseLong(memberId)))
                .flatMap(v ->
                        membershipAdaptor.suspendMemberShip(status, isActive,
                                v.getMemberNumber(), Integer.parseInt(suspendMemberPayload.getCustomerId())))
                .subscribe();

        log.info("end of suspendMemberProfile in memberService");
    }

    public void updateMemberHash(ServiceParameters<UpdateHashRequest> build) {
        UpdateHashRequest updateHashRequest = build.getPayload().get();

        memberRepository.findMemberByMemberIDByHashCD(updateHashRequest.getMemberid(), updateHashRequest.getHashCd())
                .map(v -> {
                    v.setHash_cd(updateHashRequest.delete_fl ? null : updateHashRequest.getHashCd());
                    return v;
                }).flatMap(memberRepository::save).flatMap(v -> updateLoginActivity(v.getMemberId(), updateHashRequest.delete_fl ? "Profile is Activated" : "Profile is Pending for Activation")).subscribe();
    }

    public void createMemberAddress_1(ServiceParameters<CreateUpdateMember> payload) {
        CreateUpdateMember createUpdateMember = payload.getPayload().get();
        if (createUpdateMember.getUserId() != null) {
            Mono.just(new MemberUser(createUpdateMember.getUserId(), createUpdateMember.getMemberId(), true)).flatMap(memberUserRepository::save)
                    .flatMap(v -> {
                        log.info("Inserting Member Address {}", v.getMember_id());
                        final MemberAddress memberAddress = new MemberAddress(true);
                        memberAddress.setMember_id(v.getMember_id());
                        memberAddress.setPostal_code(createUpdateMember.getPostalCode());
                        memberAddress.setCountry_code(createUpdateMember.getCountry());
                        memberAddress.setCity(createUpdateMember.getCity());
                        memberAddress.setState(createUpdateMember.getState());
                        memberAddress.setAddress_line_1(createUpdateMember.getAddressLine1());
                        memberAddress.setAddress_line_2(createUpdateMember.getAddressLine2());
                        memberAddress.setAddress_line_3(createUpdateMember.getAddressLine3());
                        return Mono.just(memberAddress);
                        /*return memberAddressRepository.findMemberAddressByMemberId(createUpdateMember.getMemberId())
                                .map(mem -> {
                                    log.info("Member ID already exists {}", mem.getMember_id());
                                    mem.setPostal_code(createUpdateMember.getPostalCode());
                                    mem.setCountry_code(createUpdateMember.getCountry());
                                    mem.setCity(createUpdateMember.getCity());
                                    mem.setState(createUpdateMember.getState());
                                    mem.setAddress_line_1(createUpdateMember.getAddressLine1());
                                    mem.setAddress_line_2(createUpdateMember.getAddressLine2());
                                    mem.setAddress_line_3(createUpdateMember.getAddressLine3());
                                    return mem;
                                }).switchIfEmpty(Mono.just(memberAddress));*/
                    }).flatMap(a ->
                    memberAddressRepository.findMemberAddressByMemberId(createUpdateMember.getMemberId()).flatMap(v -> {
                        log.info("Record already exist's in Member Address table.");
                        a.setIs_new(false);
                        return Mono.just(a);
                    }).switchIfEmpty(Mono.just(a))).flatMap(memberAddressRepository::save).flatMap(v -> updateLoginActivity(v.getMember_id(), "Creating User"))
                    .flatMap(v -> memberRepository.findMemberByMemberID(v.getMemberId())).map(v -> messageSender.send(v.getEmailId(), String.join(" ", v.getFirstName()==null?"":v.getFirstName(), v.getLastName()==null?"":v.getLastName())))
                    .subscribe();
        } else {
            if (StringUtils.trimToNull(createUpdateMember.getFName()) != null || StringUtils.trimToNull(createUpdateMember.getLName()) != null) {
                memberRepository.findMemberByMemberID(createUpdateMember.getMemberId())
                        .map(member -> {
                            member.setFirstName(createUpdateMember.getFName());
                            member.setLastName(createUpdateMember.getLName());
                            return member;
                        })
                        .flatMap(memberRepository::save).flatMap(v -> updateLoginActivity(v.getMemberId(), "Updated First & Last Name")).subscribe();
            }
            memberAddressRepository.findMemberAddressByMemberId(createUpdateMember.getMemberId()).map(v -> {
                v.setAddress_line_1(createUpdateMember.getAddressLine1());
                v.setAddress_line_2(createUpdateMember.getAddressLine2() == null ? v.getAddress_line_2() : createUpdateMember.getAddressLine2());
                v.setAddress_line_3(createUpdateMember.getAddressLine2() == null ? v.getAddress_line_3() : createUpdateMember.getAddressLine3());
                v.setCity(createUpdateMember.getCity());
                v.setCountry_code(createUpdateMember.getCountry());
                v.setState(createUpdateMember.getState());
                v.setPostal_code(createUpdateMember.getPostalCode());
                v.setIs_new(false);
                return v;
            }).flatMap(memberAddressRepository::save).flatMap(v -> updateLoginActivity(v.getMember_id(), "Updated Address")).subscribe();
        }
    }

    @Override
    public Mono<ServiceResponse> deleteMemberById(ServiceParameters<CancelRequestPayload> payload) {
        ServiceParameters<String> serviceParameters = ServiceParameters.<String>builder()
                .headers(payload.getHeaders())
                .build();
        return ruleEngineAdaptor.checkIfLightFamiliesActivated(serviceParameters)
                .flatMap(lightFamiliesActivated -> lightFamiliesActivated ? deleteMembership(payload) : deleteMember(payload))
                .map(memberDeleted -> ServiceResponse.builder().noOfRows(memberDeleted).build());
    }

    private Mono<Integer> deleteMembership(ServiceParameters<CancelRequestPayload> payload) {
        ServiceParameters<String> parameters = ServiceParameters.<String>builder()
                .headers(payload.getHeaders())
                .parameters(RequestParameters.builder()
                        .param(Constants.MEMBER_ID, String.valueOf(payload.getPayload().get().getMemberId()))
                        .build())
                .build();

        AtomicInteger membersDeleted = new AtomicInteger(0);
        return memberMembershipAdaptor.findActiveMembership(parameters)
                .onErrorResume(throwable -> {
                    deleteMember(payload).subscribe(membersDeleted::set);
                    return Mono.empty();
                })
                .map(MemberMembershipDomain::getMembershipId)
                .map(membershipId -> setMembershipId(parameters, membershipId))
                .flatMap(membershipService::deactivateMembership)
                .switchIfEmpty(Mono.just(membersDeleted.get()));
    }

    private Mono<Integer> deleteMember(ServiceParameters<CancelRequestPayload> payload) {
        ServiceParameters<String> parameters = ServiceParameters.<String>builder()
                .headers(payload.getHeaders())
                .parameters(payload.getParameters())
                .build();
        return memberAdaptor.findActiveMemberId(parameters)
                .map(publisMsgForhHistory(parameters))
                .flatMap(attributeAdaptor::deactivateForMemberId)
                .flatMap(attributesUpdated -> memberMembershipAdaptor.deactivateMembership(payload))
                .flatMap(membershipUpdated -> svcChannelAccountAdaptor.deactivateForMemberId(parameters))
                .flatMap(memberUpdated -> benefitSubscriptionService.deactivateSubscription(payload))
                .flatMap(subscriptionsDeactivated -> memberAdaptor.deactivateMember(payload));
    }

    private Mono<LoginActivity> updateLoginActivity(Long memberId, String message) {
        return Mono.just(new LoginActivity(memberId, message, LocalDateTime.now())).flatMap(loginActivityRepository::save);
    }

    @Override
    @Async("poeThreadExecutor")
    public void createPOE(ServiceParameters<MembershipPayload> payload) {
        List<ProofOfEnrollment> poeToSave = MembershipUtil.buildPOEFromMembership(payload.getPayload().get());
        producer.sendMessage(poeToSave);
    }

    @Override
    public Mono<Boolean> processCreateMemberEvent(ServiceParameters<MemberProfilePayload> payload) {
        return membershipAdaptor.checkIfMembershipExists(ServiceParameters.<String>builder()
                .headers(payload.getHeaders())
                .parameters(payload.getParameters())
                .build())
                .flatMap(exists -> producer.sendEvent(payload, MEMBERSHIP_CREATE_TOPIC_NAME, CREATE_TYPE, CREATE_MEMBER_PROFILE));
    }

    @Async("poeThreadExecutor")
    public void createPOEForMember(ServiceParameters<MemberProfilePayload> payload) {
        ProofOfEnrollment poeToSave = MembershipUtil.buildPOE(payload.getPayload().get());
        producer.sendMessage(Collections.singletonList(poeToSave));
    }

    @Override
    public Mono<Boolean> processDeleteMemberById(ServiceParameters<CancelRequestPayload> payload) {
        return fetchMemberId(payload.getParameters().getParam(Constants.ID_TYPE),
                payload.getParameters().getParam(Constants.MEMBER_ID), payload)
                .switchIfEmpty(Mono.error(new InvalidMemberIdException(payload.getHeaders().getCorrelationId())))
                .map(memberId -> preProcessor.addMemberId(payload, memberId))
                .flatMap(params -> producer.sendEvent(params, MEMBERSHIP_DELETE_TOPIC_NAME, DELETE_TYPE, DELETE_MEMBER_BY_ID));
    }

    @Override
    public Mono<Boolean> processUpdateMembersProfile(ServiceParameters<MemberProfilePayload> payload) {
        MemberProfilePayload memberProfile = payload.getPayload().get();
        return fetchMemberId(memberProfile.getIdType(), payload.getParameters().getParam(Constants.MEMBER_ID), payload)
                .switchIfEmpty(Mono.error(new InvalidMemberIdException(memberProfile.getCorrelationId())))
                .map(setMemberIdToPayload(memberProfile))
                .flatMap(params -> producer.sendEvent(payload, MEMBERSHIP_UPDATE_TOPIC_NAME, UPDATE_TYPE, UPDATE_MEMBER_PROFILE));
    }

    @Override
    public Mono<GenericResponse<Long>> processActivateMembersProfile(ServiceParameters<ActivateMemberPayload> payload) {
        ActivateMemberPayload activateMemberProfile = payload.getPayload().get();
        return memberAdaptor.findMemberIdFromEmailAddress(activateMemberProfile).flatMap(v -> {
            if (!v.getIsActive() && activateMemberProfile.getActivationcd().equals(v.getActivationCd()))
                return producer.sendEvent(payload, MEMBER_ACTIVATE_TOPIC_NAME, UPDATE_TYPE, ACTIVATE_MEMBER).map(aBoolean ->
                        new GenericResponse<>(aBoolean, HttpStatus.OK.value(), aBoolean ? "Activated" : "Error in Activation", null, v.getMemberId()));
            else
                return Mono.just(new GenericResponse<>(false, HttpStatus.OK.value(),
                        !activateMemberProfile.getActivationcd().equals(v.getActivationCd()) ? "Please enter the correct Activation Code Please note: activation code will be 6 digit random number. not alpha numeric." : v.getIsActive() ? "Already Activated" : "Can't be activated.",
                        null, v.getIsActive() ? v.getMemberId() : null));
        });
    }
    @Override
    public Mono<GenericResponse<Boolean>> processSuspendMembersProfile(ServiceParameters<SuspendMemberPayload> payload) {
        //String memberId = payload.getPayload().get().getMemberId();
        return producer.sendEvent(payload, MEMBER_SUSPEND_TOPIC_NAME, UPDATE_TYPE, SUSPEND_MEMBER).map(aBoolean ->
                new GenericResponse<>(aBoolean, HttpStatus.OK.value(), aBoolean ? "Member suspended Successfully" : "Can't be suspended.", null, aBoolean));

    }
    @Override
    public Mono<GenericResponse<String>> processBulkUploadMembersProfile(ServiceParameters<BulkUploadMember> payload) {
        String fileName = payload.getPayload().get().getFileName();
        return producer.sendEvent(payload, MEMBER_BULK_UPLOAD_TOPIC_NAME, CREATE_TYPE, BULKUPLOAD_MEMBER).map(aBoolean ->
                new GenericResponse<>(aBoolean, HttpStatus.OK.value(), aBoolean ? "File uploaded successfully." : "Issue while uploading file.", null, fileName));

    }

    public Mono<GenericResponse<Long>> createOrUpdateMemberProfile(ServiceParameters<CreateUpdateMember> createUpdateMember) {
        Long memberId = createUpdateMember.getPayload().get().getMemberId();
        return producer.sendEvent(createUpdateMember, MEMBER_CREATE_TOPIC_NAME, CREATE_TYPE, CREATE_MEMBER).map(aBoolean ->
                new GenericResponse<>(aBoolean, HttpStatus.OK.value(), aBoolean ? "Profile Updated Successfully" : "Can't be Updated.", null, memberId));
//        return memberAdaptor.checkMemberIdExists(memberId).flatMap(v -> producer.sendEvent(createUpdateMember, MEMBER_CREATE_TOPIC_NAME, CREATE_TYPE, CREATE_MEMBER).map(aBoolean -> new GenericResponse<>(aBoolean, HttpStatus.OK.value(), aBoolean ? "Updated" : "Can't be Updated.", null, memberId)));
    }

    @Override
    public Mono<GenericResponse<Integer>> onBoardMemberMembershipEmail(ServiceParameters<MemberMembershipOnBoardRequest> parameters) {
        return producer.sendEvent(parameters, MEMBER_CREATE_TOPIC_NAME, CREATE_TYPE, ONBOARD_MEMBER_MEMBERSHIP).map(aBoolean ->
                new GenericResponse<>(aBoolean, HttpStatus.OK.value(), aBoolean ? "Members has been created successfully." : "Can't be Updated.", null, parameters.getPayload().get().getCustomerId()));
    }
    @Override
    public Mono<Boolean> onBoardMemberMership(ServiceParameters<MemberMembershipOnBoardRequest> parameters) {
        return producer.sendEvent(parameters, MEMBER_CREATE_TOPIC_NAME, CREATE_TYPE, ONBOARD_MEMBER_MEMBERSHIP);
    }


    @Override
    public Mono<Boolean> enrollMember(ServiceParameters<EnrollRequest> parameters) {
        EnrollRequest enrollRequest = parameters.getPayload().get();
        return benPkgSelectionMappingAdoptor.prevalidationEnroll(enrollRequest).filter(target -> !target).flatMap(v ->
                producer.sendEvent(parameters, MEMBER_CREATE_TOPIC_NAME, CREATE_TYPE, ENROLL_MEMBER));
    }

    @Override
    public Mono<GenericResponse<Boolean>> updateMemberHashCode(ServiceParameters<UpdateHashRequest> updateHashRequestServiceParameters) {
        return producer.sendEvent(updateHashRequestServiceParameters, MEMBER_CREATE_TOPIC_NAME, CREATE_TYPE, UPDATE_MEMBER).map(aBoolean -> new GenericResponse<>(aBoolean, HttpStatus.OK.value(), aBoolean ? "Updated" : "Can't be Updated.", null, aBoolean));
    }
    private void publish(ServiceResponse memberProfileResponse) {
        // handle inbox
        log.info("Response :" + memberProfileResponse);
    }
    private Mono<ServiceResponse> handleError(Throwable throwable, BasePayload payload) {
        // error handling2
        log.error("Error Occurred :", throwable);
        return Mono.just(ServiceResponse.builder()
                .status(ResponseStatus.FAILED)
                .resourceType(ResourceType.MEMBER)
                .correlationId(payload.getCorrelationId())
                .failureReason(throwable.getMessage())
                .build());
    }

    public void onBoardMembersSave(MemberMembershipOnBoardRequest request) {
        GenericResponse genericResponse = getSolicitationList(request.getAuthorization(),request.getCustomerId(), request.getSolicitationId());
        Long packageId = Long.parseLong((String) ((LinkedHashMap)((LinkedHashMap)((List)((LinkedHashMap) genericResponse.getData()).get("solicitationPackage")).get(0)).get("packages")).get("packageId"));
        List<String> packageBenefitIds = new ArrayList<>();
        List<Map<String,String>> listOfPackageBenefitId = (List<Map<String, String>>) ((LinkedHashMap) ((LinkedHashMap) ((List) ((LinkedHashMap) genericResponse.getData()).get("solicitationPackage")).get(0)).get("packages")).get("packagesBenefits");
        for(Map<String,String> map: listOfPackageBenefitId){
            packageBenefitIds.add(map.get("packageBenefitId"));
        }
        Map<String,String> emailIdActivationCode = new HashMap<>();
        Mono<List<EnrollRequest>> enrolled = Flux.fromIterable(request.getEmailIds()).flatMap(email -> {
            Member member = new Member();
            member.setEmailId(email);
            member.setIsActive(false);
            member.setCreatedBy(request.getCustomerId().toString());
            member.setActivationCd(RandomStringUtils.randomNumeric(6));
            emailIdActivationCode.put(email,member.getActivationCd());
            member.setCreatedBy(request.getCustomerId().toString());
            member.setMemberNumber(member.getActivationCd());
            member.setCreatedDate(Instant.now());
            return Mono.just(member);
        }).flatMap(memberRepository::save)
                .flatMap(member -> {
                    Membership membership = new Membership();
                    membership.setIsActive(false);
                    membership.setStatus(ENROLLED);
                    membership.setCreatedBy(String.valueOf(request.getCustomerId()));
                    membership.setMembershipNumber(member.getActivationCd());
                    membership.setMemberId(member.getMemberId());
                    membership.setSolicitationId(request.getSolicitationId());
                    membership.setCustomerId(request.getCustomerId());
                    membership.setCreatedDate(Instant.now());
                    return Mono.just(membership);
                }).flatMap(membershipRepository::save)
                .map(membership -> {
                    EnrollRequest enrollRequest = new EnrollRequest();
                    enrollRequest.setMembershipId(membership.getMembershipId());
                    enrollRequest.setMemberId(membership.getMemberId());
                     enrollRequest.setPackageId(packageId);
                     enrollRequest.setPackageBenefitId(packageBenefitIds);
                    return enrollRequest;
                }).collectList();

        enrolled.flatMapMany(Flux::fromIterable).flatMap(member -> {
            callEnrollMemberMembership(member);
            return Mono.just(member);
        }).subscribe();
        ResponseEntity<Object> customer = getCustomer(request.getAuthorization(),String.valueOf(request.getCustomerId()));
        String communPref = (String) ((LinkedHashMap)((LinkedHashMap)customer.getBody()).get("customer")).get("communicationPreferences");
        if(communPref.toLowerCase().contains("email")) {
            for (Map.Entry<String, String> entry : emailIdActivationCode.entrySet()) {
                messageSender.onboardSend(entry.getKey(), entry.getValue());
            }
        }
    }

    private void callEnrollMemberMembership(EnrollRequest enrollRequest) {
        membershipService.activateMembership(enrollRequest.getMembershipId()).subscribe(this::publish);
        enrollMemberMembership(enrollRequest).map(v -> ServiceResponse.builder().build()).onErrorResume(throwable -> this.handleError(throwable, enrollRequest))
                .subscribe(this::publish);
    }

    @Override
    public Mono<List<Long>> enrollMemberMembership(EnrollRequest enrollRequest) {
        return benPkgSelectionMappingAdoptor.prevalidationEnroll(enrollRequest).filter(v -> !v)
                .flatMap(v -> membershipSubscriptionAdaptor.enrollMemberMembership(enrollRequest))
                .map(v -> {
                    enrollRequest.setMemberSubscriptionId(v);
                    return enrollRequest;
                }).flatMap(benPkgSelectionMappingAdoptor::enrollMemberMembership);
    }

    private Mono<ServiceResponse> updateFulfillment(MemberProfilePayload payload) {
        return Flux.fromStream(MembershipUtil.buildFulfillmentPref(payload))
                .subscribeOn(Schedulers.parallel())
                .map(memberFulfillmentPrefPayload -> ServiceParameters.<MemberFulfillmentPrefPayload>builder()
                        .payload(RequestPayload.<MemberFulfillmentPrefPayload>builder().payload(memberFulfillmentPrefPayload).build())
                        .build())
                .map(fulfillmentService::updateMemberFulfillmentPreference)
                .count()
                .map(updated -> ServiceResponse.builder()
                        .correlationId(payload.getCorrelationId())
                        .resourceType(ResourceType.MEMBER)
                        .status(ResponseStatus.COMPLETED)
                        .noOfRows(updated.intValue())
                        .build());
    }

    private Mono<Long> fetchMemberId(String idType, String id, ServiceParameters parameters) {
        if (Optional.ofNullable(idType)
                .map(Constants.EXTERNAL_MEMBER_REF_NO::equalsIgnoreCase)
                .isPresent()) {
            return memberAdaptor.findMemberIdForRefNum(ServiceParameters.<String>builder()
                    .headers(parameters.getHeaders())
                    .parameters(RequestParameters.builder()
                            .param(Constants.EXTERNAL_MEMBER_REF_NO, id)
                            .build())
                    .build())
                    .switchIfEmpty(Mono.error(new MemberNotFoundException(parameters.getHeaders().getCorrelationId())));
        } else {
            return memberAdaptor.findActiveMemberId(ServiceParameters.<String>builder()
                    .headers(parameters.getHeaders())
                    .parameters(RequestParameters.builder()
                            .param(Constants.MEMBER_ID, id)
                            .build())
                    .build())
                    .switchIfEmpty(Mono.error(new MemberNotFoundException(parameters.getHeaders().getCorrelationId())))
                    .map(MemberDomain::getMemberId);
        }
    }

    private Function<MemberDomain, ServiceParameters<String>> publisMsgForhHistory(ServiceParameters<String> parameters) {
        return member -> {
            historyService.publishForMember(member);
            return parameters;
        };
    }
    @Override
    public GenericResponse getSolicitationList(String authorization,Integer customerId,Integer solicitationId){
         String query ="{\n" +
        "solicitationPackage(customer_id:\"dynamic_customer_id\",solicitation_id:\"dynamic_solicitation_id\"){\n" +
        "packages{\n" +
                "        packageId\n" +
                "        packageName\n" +
                "        correlationId\n" +
                "          packagesBenefits{\n" +
                "            packageBenefitId\n" +
                "          }\n" +
                "          }\n" +
                "        }\n" +
                "}\n";
        query=query.replace("dynamic_solicitation_id",solicitationId.toString());
        query=query.replace("dynamic_customer_id",customerId.toString());
        GenericResponse data= customerService.getSolictiation(authorization,query);
        return data;

    }
    @Override
    public ResponseEntity<Object> getCustomer(String authorization,String customerId){
        String query ="{\n" +
                "customer(id:\"dynamic_customer_id\"){\n" +
                "\tcommunicationPreferences\n" +
                "}\n" +
                "}";
        query=query.replace("dynamic_customer_id",customerId);
        ResponseEntity<Object> customer = customerService.getCustomer(authorization, query);
        return customer;

    }

    @Override
    public void bulkUploadMember(BulkUploadMember payload){
        String fileName = payload.getFileName();
        String customerId = payload.getCustomerId();
        String solicitationId=  payload.getSolicitationId();
        GenericResponse genericResponse = getSolicitationList(payload.getAuthorization(),Integer.parseInt(customerId), Integer.parseInt(solicitationId));
        Long packageId = Long.parseLong((String) ((LinkedHashMap)((LinkedHashMap)((List)((LinkedHashMap) genericResponse.getData()).get("solicitationPackage")).get(0)).get("packages")).get("packageId"));
        List<String> packageBenefitIds = new ArrayList<>();
        List<Map<String,String>> listOfPackageBenefitId = (List<Map<String, String>>) ((LinkedHashMap) ((LinkedHashMap) ((List) ((LinkedHashMap) genericResponse.getData()).get("solicitationPackage")).get(0)).get("packages")).get("packagesBenefits");
        for(Map<String,String> map: listOfPackageBenefitId){
            packageBenefitIds.add(map.get("packageBenefitId"));
        }

        ResponseEntity<Object> customer = getCustomer(payload.getAuthorization(),payload.getCustomerId());
        String communPref = (String) ((LinkedHashMap)((LinkedHashMap)customer.getBody()).get("customer")).get("communicationPreferences");

        try{
            FileInputStream excelFile = new FileInputStream(new File(rootPath+"//"+fileName));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet worksheet = workbook.getSheetAt(0);

            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                Member member = new Member();
                Row row = worksheet.getRow(i);
                member.setEmailId(new DataFormatter().formatCellValue(row.getCell(0)));
                member.setFirstName(new DataFormatter().formatCellValue(row.getCell(1)));
                member.setLastName(new DataFormatter().formatCellValue(row.getCell(2)));
                member.setActivationCd(RandomStringUtils.randomNumeric(6));
                member.setMemberNumber(member.getActivationCd());
                member.setCreatedDate(Instant.now());
                member.setCreatedBy(customerId);
                member.setIsActive(false);
                if(communPref.toLowerCase().contains("email"))  {
                    messageSender.onboardSend(member.getEmailId(), member.getActivationCd());
                }
                Mono.just(member).flatMap(memberRepository::save)
                        .flatMap(m -> {
                            Membership membership = new Membership();
                            membership.setIsActive(false);
                            membership.setStatus(ENROLLED);
                            membership.setCreatedBy(customerId);
                            membership.setMembershipNumber(member.getActivationCd());
                            membership.setMemberId(member.getMemberId());
                            membership.setSolicitationId(Integer.parseInt(solicitationId));
                            membership.setCustomerId(Integer.parseInt(customerId));
                            membership.setCreatedDate(Instant.now());
                            return Mono.just(membership);
                        }).flatMap(membershipRepository::save)
                        .flatMap(mship ->{
                            MemberAddress address = new MemberAddress();
                            address.setMember_id(mship.getMemberId());
                            address.setMembershipId(mship.getMembershipId());
                            address.setAddress_line_1(new DataFormatter().formatCellValue(row.getCell(3)));
                            address.setAddress_line_2(new DataFormatter().formatCellValue(row.getCell(4)));
                            address.setAddress_line_3(new DataFormatter().formatCellValue(row.getCell(5)));
                            address.setCity(new DataFormatter().formatCellValue(row.getCell(6)));
                            address.setState(new DataFormatter().formatCellValue(row.getCell(7)));
                            address.setCountry_code(new DataFormatter().formatCellValue(row.getCell(8)));
                            address.setPostal_code(new DataFormatter().formatCellValue(row.getCell(9)));
                            address.setIs_new(true);
                            return Mono.just(address);
                        }).flatMap(memberAddressRepository::save).map(address -> {
                    EnrollRequest enrollRequest = new EnrollRequest();
                    enrollRequest.setMembershipId(address.getMembershipId());
                    enrollRequest.setMemberId(address.getMember_id());
                    enrollRequest.setPackageId(packageId);
                    enrollRequest.setPackageBenefitId(packageBenefitIds);
                    callEnrollMemberMembership(enrollRequest);
                    return enrollRequest;
                }).subscribe();
                log.info("End of bulkUploadMember in member service");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
