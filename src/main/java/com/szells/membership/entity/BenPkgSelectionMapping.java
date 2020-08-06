package com.szells.membership.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Table("ben_pkg_selection_mapping")
public class BenPkgSelectionMapping extends CommonAttributes {
    @Column("ben_pkg_selection_mapping_id")
    @Id
    private Long benPkgSelectionMappingId;
    @Column("member_subscription_id")
    private Long member_subscription_id;
    @Column("is_active")
    private Boolean active;
    @Column("mem_sub_flx_pkg_dtl_id")
    private Long memSubFlxPkgDtlId;
}
