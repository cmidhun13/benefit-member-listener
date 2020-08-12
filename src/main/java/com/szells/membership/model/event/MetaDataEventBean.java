package com.szells.membership.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MetaDataEventBean {
    @JsonProperty("event_name")
    private final String eventName;
    @JsonProperty("source_event_published")
    private final String sourceEventPublished;
    @JsonProperty("is_reporting")
    private final ReportingBean isReporting;
    @JsonProperty("is_history")
    private final HistoryBean isHistory;
    @JsonProperty("audit")
    private final EventAuditBean eventAuditBean;
}
