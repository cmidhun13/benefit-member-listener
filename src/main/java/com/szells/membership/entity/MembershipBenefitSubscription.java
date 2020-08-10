package com.szells.membership.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Getter
@Setter
@Table("membership_benefit_subscription")
public class MembershipBenefitSubscription extends CommonAttributes {
    @Column("member_subscription_id")
    @Id
    private Long id;
    @Column("member_id")
    private Long memberId;
    @Column("membership_id")
    private Long membershipId;
    @Column("visibility_scope_id")
    private Long visibilityScopeId;
    @Column("benefit_key")
    private String benefitKey;
    private String status;
    @Column("start_date")
    private Instant startDate;
    @Column("end_date")
    private Instant endDate;
    @Column("benefit_ref")
    private String benefitRef;
    @Column("cancelled_date")
    private Instant cancelledDate;
    @Column("cancelled_reason")
    private String cancelledReason;
    @Column("is_active")
    private Boolean isActive;
}
