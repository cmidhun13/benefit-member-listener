package com.szells.membership.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class MembershipSubscriptionDomain extends CommonAttributes {

    private Long id;
    private Long memberId;
    private Long membershipId;
    private Long visibilityScopeId;
    private String benefitKey;
    private String status;
    private Instant startDate;
    private Instant endDate;
    private String benefitRef;
    private Instant cancelledDate;
    private String cancelledReason;
    private Boolean isActive;
}
