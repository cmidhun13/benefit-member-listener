package com.szells.membership.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
class CommonAttributes {

    private String createdBy;
    private Date createdDate;

    private String updatedBy;
    private Date updatedDate;

    private String correlationId;
}
