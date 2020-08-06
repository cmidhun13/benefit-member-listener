package com.szells.membership.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class MemberSubscriptionHistory {

    @JsonProperty("correlation_id")
    private final String correlationId;
    @JsonProperty("meta_data")
    private final MetaData metaData;
    private final Map<String, Object> data;
}
