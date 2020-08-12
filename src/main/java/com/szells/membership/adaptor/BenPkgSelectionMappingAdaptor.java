package com.szells.membership.adaptor;

import com.szells.membership.model.request.EnrollRequest;
import com.szells.membership.entity.BenPkgSelectionMapping;
import com.szells.membership.repository.IBenPkgSelectionMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BenPkgSelectionMappingAdaptor implements IBenPkgSelectionMappingAdoptor {
    private final IBenPkgSelectionMappingRepository repository;

    @Override
    public Mono<List<Long>> enrollMemberMembership(EnrollRequest enrollRequest) {
        return Flux.fromIterable(enrollRequest.getMemberSubscriptionId()).map(v -> {
            BenPkgSelectionMapping benPkgSelectionMapping = new BenPkgSelectionMapping();
            benPkgSelectionMapping.setActive(true);
            benPkgSelectionMapping.setMember_subscription_id(v);
            benPkgSelectionMapping.setMemSubFlxPkgDtlId(enrollRequest.getPackageId());
            return benPkgSelectionMapping;
        }).flatMap(repository::save).map(BenPkgSelectionMapping::getBenPkgSelectionMappingId).collectList();
    }

    @Override
    public Mono<Boolean> prevalidationEnroll(EnrollRequest enrollRequest) {
        return repository.checkIfPackageEnrolled(enrollRequest.getMemberId(), enrollRequest.getMembershipId(), enrollRequest.getPackageBenefitId(), enrollRequest.getPackageId());
    }
}
