//For now we are comment against Standing data later we uncomment
package com.szells.membership.adaptor;

import com.szells.membership.model.BenefitPackageResponse;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Flux;

public interface IStandingDataAdaptor {
    Flux<BenefitPackageResponse> findPackagesBySolicitationId(ServiceParameters<String> parameters);
}

