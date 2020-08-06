package com.szells.membership.service;

import com.szells.membership.domain.MemberFulfillmentPrefPayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.Disposable;

public interface IMemberFulfillmentService {

    Disposable updateMemberFulfillmentPreference(ServiceParameters<MemberFulfillmentPrefPayload> params);

}
