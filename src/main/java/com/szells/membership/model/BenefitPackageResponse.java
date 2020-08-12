package com.szells.membership.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
