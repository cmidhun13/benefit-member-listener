package com.szells.membership.service;

import com.szells.membership.adaptor.IMembershipSubscriptionAdaptor;
import com.szells.membership.adaptor.IRuleEngineAdaptor;
import com.szells.membership.adaptor.IStandingDataAdaptor;
import com.szells.membership.domain.MembershipSubscriptionDomain;
import com.szells.membership.domain.payload.CancelRequestPayload;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.membership.mapper.IMembershipSubscriptionMapper;
import com.szells.util.cache.VisibilityScopeCache;
import com.szells.util.domain.RequestParameters;
import com.szells.util.domain.RequestPayload;
import com.szells.util.domain.ServiceParameters;
import com.szells.util.domain.VisibilityScopeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.szells.membership.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class BenefitSubscriptionService implements IBenefitSubscriptionService {
    private final IMembershipSubscriptionAdaptor adaptor;
    private final IRuleEngineAdaptor ruleEngineAdaptor;
    private final IMembershipSubscriptionMapper mapper;
    private final IStandingDataAdaptor standingDataAdaptor;

    @Override
    public Mono<Integer> deactivateSubscription(ServiceParameters<CancelRequestPayload> parameters) {

        return ruleEngineAdaptor.findMembershipStatusValForClient(ServiceParameters.<String>builder()
                .headers(parameters.getHeaders())
                .parameters(RequestParameters.builder()
                        .param(MEMBERSHIP_STATUS, DEFAULT_CANCELLED_STATUS)
                        .param(DEFAULT_STATUS, CANCELLED)
                        .build())
                .build())
                .map(status -> ServiceParameters.<CancelRequestPayload>builder()
                        .headers(parameters.getHeaders())
                        .payload(parameters.getPayload())
                        .parameters(RequestParameters.builder()
                                .param(MEMBERSHIP_STATUS, status)
                                .build())
                        .build())
                .flatMap(adaptor::cancelSubscriptions);
    }

    public Mono<List<MembershipSubscriptionDomain>> createSubscriptionForMember(ServiceParameters<MemberProfilePayload> parameters) {
        AtomicBoolean isH2NGClient = new AtomicBoolean(false);
        MemberProfilePayload memberProfilePayload = parameters.getPayload().get();
        VisibilityScopeCache.getInstance().getCacheEntry(parameters.getHeaders().getVisibilityScopeKey())
                .map(VisibilityScopeData::isH2NGClient)
                .ifPresent(isH2NGClient::set);
        if (isH2NGClient.get()) {

            // For H2NG Clients fetch the benefit Key from Standing Data using solicitation Id
            return standingDataAdaptor.findPackagesBySolicitationId(ServiceParameters.<String>builder()
                    .headers(parameters.getHeaders())
                    .parameters(RequestParameters.builder()
                            .param(SOLICITATION_ID, memberProfilePayload.getSolicitationRef())
                            .build())
                    .build())
                    .map(benefitPackage -> mapper.toDomain(NEW, String.valueOf(benefitPackage.getBenefitId()), memberProfilePayload))
                    .map(subscription -> ServiceParameters.<MembershipSubscriptionDomain>builder()
                            .headers(parameters.getHeaders())
                            .payload(RequestPayload.<MembershipSubscriptionDomain>builder()
                                    .payload(subscription)
                                    .build())
                            .build())
                    .flatMap(adaptor::createSubscriptions)
                    .collectList();
        } else {

            // Fetch the status to be used for new subscription for client from Rule Engine!
            Flux<String> subscriptionStatusForClient = ruleEngineAdaptor.findMembershipStatusValForClient(ServiceParameters.<String>builder()
                    .headers(parameters.getHeaders())
                    .parameters(RequestParameters.builder()
                            .param(MEMBERSHIP_STATUS, DEFAULT_NEW_STATUS)
                            .param(DEFAULT_STATUS, NEW)
                            .build())
                    .build()).flux();

            // Fetch the Solicitation ids for the client from Rule Engine!
            Flux<String> solicitationForClient = ruleEngineAdaptor.getSolicitationForClient(ServiceParameters.<String>builder()
                    .headers(parameters.getHeaders())
                    .parameters(RequestParameters.builder()
                            .param(SOLICITATION_ID, memberProfilePayload.getSolicitationRef())
                            .build())
                    .build());

            // Chain above two and create subscriptions!
            return subscriptionStatusForClient.zipWith(solicitationForClient,
                    (status, solicitationId) -> mapper.toDomain(status, solicitationId, memberProfilePayload))
                    .map(subscription -> ServiceParameters.<MembershipSubscriptionDomain>builder()
                            .headers(parameters.getHeaders())
                            .payload(RequestPayload.<MembershipSubscriptionDomain>builder()
                                    .payload(subscription)
                                    .build())
                            .build())
                    .flatMap(adaptor::createSubscriptions)
                    .collectList();
        }
    }

}
