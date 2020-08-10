package com.szells.membership.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MemberFulfillmentPref extends CommonAttributes {

    private Long id;

    private Long memberId;

    private Long membershipId;

    private String commCategory;

    private String fulfillmentType;

    private String commPreferenceSource;

    private Boolean optOutFlag;

    private LocalDateTime optOutStart;

    private LocalDateTime optOutEnd;

}
