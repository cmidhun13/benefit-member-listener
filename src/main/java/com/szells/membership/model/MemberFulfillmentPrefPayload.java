package com.szells.membership.model;

import com.szells.membership.model.payload.MemberAttributeBean;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberFulfillmentPrefPayload {
    private final String correlationId;
    private final Long membershipId;
    private final Long memberId;
    private final String fulfillmentType;
    private final String commCategory;
    private final Boolean otpOutFl;

    private final MemberAttributeBean.AuditBean audit;
}
