package com.szells.membership.domain.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AttributeBean {

    private String name;
    private String value;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_on")
    private Date createdOn;

}
