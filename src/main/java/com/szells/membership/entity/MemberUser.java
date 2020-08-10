package com.szells.membership.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;

//@EqualsAndHashCode(callSuper = true)
@Data
@Table("member_user")
@RequiredArgsConstructor
@AllArgsConstructor
public class MemberUser implements Persistable<String> {
    @NotNull
    @Id
    private String user_id;
    private Long member_id;
    @NotNull
    @Transient
    private Boolean isNew;

    @Override
    public String getId() {
        return user_id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
