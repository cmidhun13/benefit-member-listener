package com.szells.membership.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.szells.membership.domain.payload.BasePayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EnrollRequest extends BasePayload {
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("package_id")
    private Long packageId;
    @JsonProperty("membership_id")
    private Long membershipId;
    @JsonProperty("package_benefit_id")
    private List<String> packageBenefitId;
    @JsonProperty("member_subscription_id")
    private List<Long> memberSubscriptionId;
}
