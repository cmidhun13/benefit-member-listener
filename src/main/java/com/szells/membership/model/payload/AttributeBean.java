package com.szells.membership.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeBean {

    private String name;
    private String value;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("created_on")
    private Date createdOn;

}
