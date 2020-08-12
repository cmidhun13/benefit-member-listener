package com.szells.membership.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportingBean {
    @JsonProperty("is_reporting_required")
    private final Boolean isReportingRequired;
    @JsonProperty("catalogue_name")
    private final String catalogueName;
}
