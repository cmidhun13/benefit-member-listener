package com.szells.membership.domain.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePayload {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String visibilityScopeKey;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("correlation_id")
    private String correlationId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long visibilityScopeId;
}
