package com.szells.membership.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class Membership extends CommonAttributes {
    @Column("membership_id")
    @Id
    private Long membershipId;

    @Column("membership_number")
    private String membershipNumber;

    @Column("sc_ext_membership_ref")
    private String externalMembershipRef;

    @Column("sc_ext_txn_ref")
    private String externalTxnRef;

    @Column("sc_ext_txn_detail")
    private String externalTxnDetail;

    private String status;

    @Column("is_active")
    private Boolean isActive;

    @Column("start_date")
    private Instant startDate;

    @Column("end_date")
    private Instant endDate;

    @Column("solicitation_ref")
    private String solicitationRef;

    @Column("member_group_type")
    private String memberGroupType;

    @Column("cancelled_reason")
    private String cancelledReason;

    @Column("reinstate_reason")
    private String reinstateReason;

    @Column("tier_change_reason")
    private String tierChangeReason;

    @Column("membership_guid")
    private String membershipGUId;

    @Column("limited_disclosure_fl")
    private Boolean limitedDisclosureFlag;

    @Column("customer_id")
    private Integer customerId;

    @Column("solicitation_id")
    private Integer solicitationId;

    @Transient
    private Long memberId;
}
