package com.szells.membership.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonPropertyOrder({"member_id", "membership_id", "events"})
public class EventDataBean {
    @JsonProperty("member_id")
    private final Object memberIds;
    @JsonProperty("membership_id")
    private final long membershipId;
    private final List<EventBean> events;
}
