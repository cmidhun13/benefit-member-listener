package com.szells.membership.adaptor;

import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

public interface ISvcChannelAccntAdaptor {
    Mono<Integer> deactivateForMemberId(ServiceParameters<String> parameters);
}
