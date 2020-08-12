package com.szells.membership.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class SourceContextPayload {

    @Schema(description = "external membership reference #", example = "AL134235245", required = true)
    @JsonProperty("external_membership_ref")
    private String externalMembershipReference;

    @Schema(description = "external transaction reference #", example = "PYMT12342342345", required = true)
    @JsonProperty("external_txn_ref")
    private String externalTxnReference;

    @Schema(description = "external transaction detail", example = "Transaction at $", required = true)
    @JsonProperty("external_txn_detail")
    private String externalTxnDetail;
}
