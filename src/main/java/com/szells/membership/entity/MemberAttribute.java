package com.szells.membership.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("member_attribute")
public class MemberAttribute extends CommonAttributes {
    @Id
    @Column("member_attribute_id")
    private Long id;
    @Column("member_id")
    private Long memberId;
    @Column("attribute_name")
    private String attributeName;
    @Column("attribute_value")
    private String attributeValue;
    @Column("is_active")
    private Boolean isActive;
}
