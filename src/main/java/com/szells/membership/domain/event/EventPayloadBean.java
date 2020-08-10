package com.szells.membership.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonPropertyOrder({"correlation_id", "meta_data", "data"})
public class EventPayloadBean {
    @JsonProperty("correlation_id")
    private final String correlationId;
    @JsonProperty("meta_data")
    private final MetaDataEventBean metaData;
    private final EventDataBean data;
}
