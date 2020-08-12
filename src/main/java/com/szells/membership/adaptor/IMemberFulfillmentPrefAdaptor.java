package com.szells.membership.adaptor;

import com.szells.membership.model.MemberFulfillmentPref;
import com.szells.membership.model.MemberFulfillmentPrefPayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.publisher.Mono;

public interface IMemberFulfillmentPrefAdaptor {
    Mono<MemberFulfillmentPref> findFulfillmentPref(ServiceParameters<MemberFulfillmentPrefPayload> params);

    Mono<MemberFulfillmentPref> saveFulfillmentPref(ServiceParameters<MemberFulfillmentPref> params);
}
