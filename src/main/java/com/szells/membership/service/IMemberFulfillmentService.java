package com.szells.membership.service;

import com.szells.membership.model.MemberFulfillmentPrefPayload;
import com.szells.util.domain.ServiceParameters;
import reactor.core.Disposable;

public interface IMemberFulfillmentService {

    Disposable updateMemberFulfillmentPreference(ServiceParameters<MemberFulfillmentPrefPayload> params);

}
