package com.szells.membership.processor;

import com.szells.membership.model.payload.CancelRequestPayload;
import com.szells.util.domain.ServiceParameters;

public interface IMembershipPreProcessor {
    ServiceParameters<CancelRequestPayload> addMemberId(ServiceParameters<CancelRequestPayload> parameters, Long memberId);

    ServiceParameters<CancelRequestPayload> buildCancelMemberParams(CancelRequestPayload cancelRequestPayload);

}
