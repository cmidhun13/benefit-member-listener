//For now we are comment against Standing data later we uncomment
package com.szells.membership.adaptor;

import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRuleEngineAdaptor {
    Mono<Boolean> checkIfLightFamiliesActivated(ServiceParameters<String> parameters);

    Mono<String> findMembershipStatusValForClient(ServiceParameters<String> parameters);

    Flux<String> getSolicitationForClient(ServiceParameters<String> parameters);
}

