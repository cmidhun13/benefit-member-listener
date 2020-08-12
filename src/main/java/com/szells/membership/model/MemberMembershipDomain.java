package com.szells.membership.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MemberMembershipDomain extends CommonAttributes {
    private Long membershipMemberId;
    private Long memberId;
    private Long membershipId;
    private Date memberJoinDate;
    private Boolean isActive;
    private long visibilityScopeId;
    private Date cancelledDate;
    private String cancelledReason;
    private Boolean isPrimary;
    private String reinstateReason;
    private Boolean webEnabled;
}
