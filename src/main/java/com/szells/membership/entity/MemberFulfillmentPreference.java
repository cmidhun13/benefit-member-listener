package com.szells.membership.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table("member_fulflmnt_preference")
public class MemberFulfillmentPreference extends CommonAttributes {

    @Column("mem_fulflmnt_pref_id")
    @Id
    private Long id;

    @Column("member_id")
    private Long memberId;

    @Column("membership_id")
    private Long membershipId;

    @Column("comm_category")
    private String commCategory;

    @Column("fulfillment_type")
    private String fulfillmentType;

    @Column("comm_preference_source")
    private String commPreferenceSource;

    @Column("opt_out_fl")
    private Boolean optOutFlag;

    @Column("optout_start")
    private LocalDateTime optOutStart;

    @Column("optout_end")
    private LocalDateTime optOutEnd;

}
