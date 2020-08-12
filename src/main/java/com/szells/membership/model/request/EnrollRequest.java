package com.szells.membership.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.szells.membership.model.payload.BasePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
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
