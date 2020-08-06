package com.szells.membership.domain.request;

import com.szells.membership.domain.payload.BasePayload;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUpdateMember extends BasePayload {
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("address_line1")
    private String addressLine1;
    @JsonProperty("address_line2")
    private String addressLine2;
    @JsonProperty("address_line3")
    private String addressLine3;
    private String city;
    private String country;
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
    @Column("created_date")
    @CreatedDate
    private LocalDate createdDate;
    @Column("updated_date")
    @LastModifiedDate
    private LocalDate updatedDate;
    @JsonProperty("first_name")
    private String fName;
    @JsonProperty("last_name")
    private String LName;
}
