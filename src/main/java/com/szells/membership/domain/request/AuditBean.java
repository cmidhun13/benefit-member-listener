package com.szells.membership.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AuditBean {
    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdOn;

    @JsonProperty("updated_by")
    private String updatedBy;

    @JsonProperty("updated_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date updatedOn;
}
