package com.szells.membership.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.szells.membership.domain.payload.BasePayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MemberMembershipOnBoardRequest extends BasePayload {
    @JsonProperty("solicitation_id")
    public Integer solicitationId;

    @JsonProperty("email_id")
    public List<String> emailIds;

    @JsonProperty("customer_id")
    private Integer customerId;
}
