package com.szells.membership.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
class CommonAttributes {

    @Column("created_by")
    @CreatedBy
    private String createdBy;

    @Column("created_date")
    @CreatedDate
    private Instant createdDate;

    @Column("updated_by")
    private String updatedBy;

    @Column("updated_date")
    @LastModifiedDate
    private Instant updatedDate;

    @Column("correlation_id")
    private String correlationId;

}
