package com.szells.membership.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class EventBean {
    @JsonProperty("event_type")
    private final String eventType;
    @JsonProperty("source_system")
    private final String sourceSystem;
    @JsonFormat(shape = Shape.STRING)
    @JsonProperty("action_date")
    private final Date actionDate;
    @JsonProperty("action_user")
    private final String actionUser;
    private final String status;
    @JsonProperty("result_code")
    private final String resultCode;
    @JsonProperty("result_details")
    private final String resultDetails;
    @JsonProperty("event_attributes")
    private final List<EventAttributeBean> eventAttributes;
    private final EventAuditBean audit;
}
