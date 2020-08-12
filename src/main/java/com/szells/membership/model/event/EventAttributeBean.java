package com.szells.membership.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class EventAttributeBean implements Serializable {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("value")
    private final String value;
}
