package com.szells.membership.adaptor;

import com.szells.membership.model.request.EnrollRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBenPkgSelectionMappingAdoptor {
    Mono<List<Long>> enrollMemberMembership(EnrollRequest enrollRequest);

    Mono<Boolean> prevalidationEnroll(EnrollRequest enrollRequest);
}
