package com.szells.membership.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HistoryBean {
    @JsonProperty("is_history_required")
    private final Boolean isHistoryRequired;
    @JsonProperty("catalogue_name")
    private final String catalogueName;
}
