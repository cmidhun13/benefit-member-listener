package com.szells.membership.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("loginactivity")
public class LoginActivity implements Persistable<Long> {
    @Column("member_id")
    @Id
    private Long memberId;
    private String message;
    private LocalDateTime activity_tm;

    @Override
    public Long getId() {
        return memberId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
