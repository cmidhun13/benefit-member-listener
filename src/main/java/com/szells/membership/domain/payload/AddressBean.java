package com.szells.membership.domain.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class AddressBean {

    @JsonProperty("address_line1")
    private String addressLine1;
    @JsonProperty("address_line2")
    private String addressLine2;
    @JsonProperty("address_line3")
    private String addressLine3;
    private String city;
    private String county;
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("country_code")
    private String countryCode;
    private MemberAttributeBean.AuditBean auditBean;
    @JsonProperty("is_active")
    private boolean isActive;

}
