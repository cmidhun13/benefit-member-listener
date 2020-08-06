package com.szells.membership.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Getter
@Setter
@Table("membership_member")
public class MemberMembership extends CommonAttributes {
    @Column("memship_member_id")
    @Id
    private Long membershipMemberId;

    @Column("member_id")
    private Long memberId;

    @Column("membership_id")
    private Long membershipId;

    @Column("member_join_date")
    private Date memberJoinDate;

    @Column("is_active")
    private Boolean isActive;

    @Column("visibilityScope_id")
    private long visibilityScopeId;

    @Column("cancelled_date")
    private Date cancelledDate;

    @Column("cancelled_reason")
    private String cancelledReason;

    @Column("is_primary")
    private Boolean isPrimary;

    @Column("reinstate_reason")
    private String reinstateReason;

    @Column("web_enabled")
    private Boolean webEnabled;
}
