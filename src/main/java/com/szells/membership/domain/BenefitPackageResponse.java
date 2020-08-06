package com.szells.membership.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BenefitPackageResponse {
    private Integer benefitPackageId;
    private Integer benefitId;
    private String flexibleFlag;
    private Integer displaySeq;
    private Object supplierReferenceName;
    private Integer isDefaultFlexibleBenefit;
    private Integer primaryOnly;
    @JsonProperty("userpaysFlag")
    private Object userPaysFlag;
    private String activeFlag;
}
