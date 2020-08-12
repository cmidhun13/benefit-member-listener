package com.szells.membership.adaptor;

import com.szells.membership.constants.Constants;
import com.szells.membership.model.MembershipSubscriptionDomain;
import com.szells.membership.model.payload.CancelRequestPayload;
import com.szells.membership.model.request.EnrollRequest;
import com.szells.membership.entity.MembershipBenefitSubscription;
import com.szells.membership.exception.MemberSaveException;
import com.szells.membership.mapper.IMembershipSubscriptionMapper;
import com.szells.membership.repository.IMembershipSubscriptionRepository;
import com.szells.util.domain.ServiceParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.szells.membership.constants.Constants.MEMBERSHIP_ID;

@Service
@RequiredArgsConstructor
public class MembershipSubscriptionAdaptor implements IMembershipSubscriptionAdaptor {

    private final IMembershipSubscriptionRepository repository;
    private final IMembershipSubscriptionMapper mapper;

    @Override
    public Mono<Integer> cancelSubscriptions(ServiceParameters<CancelRequestPayload> parameters) {
        CancelRequestPayload payload = parameters.getPayload().get();
        return repository.deactivateSubscriptionStatusForMember(payload.getMemberId(), payload.getVisibilityScopeId(),
                payload.getDeleteReason(), payload.getCancelDate().toInstant(), payload.getUpdatedBy(),
                payload.getUpdatedDate().toInstant(),
                parameters.getParameters().getParam(Constants.MEMBERSHIP_STATUS), payload.getCorrelationId());
    }

    @Override
    public Mono<MembershipSubscriptionDomain> createSubscriptions(ServiceParameters<MembershipSubscriptionDomain> parameters) {
        return Mono.just(parameters.getPayload().get())
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .switchIfEmpty(Mono.error(new MemberSaveException(parameters.getHeaders().getCorrelationId())))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Integer> deactivateMembership(ServiceParameters<String> parameters) {
        return repository.updateMembership(Long.parseLong(parameters.getParameters().getParam(MEMBERSHIP_ID)), false);
    }

    @Override
    public Mono<List<Long>> enrollMemberMembership(EnrollRequest enrollRequest) {
        return Flux.fromIterable(enrollRequest.getPackageBenefitId()).map(v -> {
            MembershipBenefitSubscription membershipBenefitSubscription = new MembershipBenefitSubscription();
            membershipBenefitSubscription.setMemberId(enrollRequest.getMemberId());
            membershipBenefitSubscription.setMembershipId(enrollRequest.getMembershipId());
            membershipBenefitSubscription.setBenefitKey(v);
            membershipBenefitSubscription.setIsActive(true);
//            membershipBenefitSubscription.setStatus("ENROLLED");
            return membershipBenefitSubscription;
        }).flatMap(repository::save).map(MembershipBenefitSubscription::getId).collectList();
    }
}
