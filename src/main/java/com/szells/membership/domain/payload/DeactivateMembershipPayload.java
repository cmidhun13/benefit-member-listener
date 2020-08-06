package com.szells.membership.domain.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeactivateMembershipPayload extends BasePayload {
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("solicitation_id")
    private Integer solicitationId;
}
