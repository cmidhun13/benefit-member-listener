package com.szells.membership.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
public class EventAuditBean implements Serializable {
    @JsonIgnore
    private static final transient long serialVersionUID = 1L;

    @JsonProperty("user_created")
    private final String userCreated;
    @JsonProperty("date_created")
    @JsonFormat(shape = Shape.STRING)
    private final Date dateCreated;
    @JsonProperty("user_modified")
    private final String userModified;
    @JsonProperty("date_modified")
    @JsonFormat(shape = Shape.STRING)
    private final Date dateModified;
}
