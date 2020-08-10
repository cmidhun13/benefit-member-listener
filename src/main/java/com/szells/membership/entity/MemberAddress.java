package com.szells.membership.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

//@EqualsAndHashCode(callSuper = true)
@Data
@Table("member_address")
@NoArgsConstructor
@RequiredArgsConstructor
public class MemberAddress implements Persistable<Long> {
    @Id
    private Long member_add_id;
    private Long member_id;
    private String address_line_2;
    private String address_line_1;
    private String address_line_3;
    private String city;
    private String state;
    private String postal_code;
    private String country_code;
    private Boolean is_active;
    @NonNull
    @Transient
    private Boolean is_new;
    @Transient
    private Long membershipId;

    @Override
    public Long getId() {
        return member_id;
    }

    @Override
    public boolean isNew() {
        return is_new;
    }
}
