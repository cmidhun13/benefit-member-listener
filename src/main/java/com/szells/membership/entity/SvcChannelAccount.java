package com.szells.membership.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("service_channel_account")
public class SvcChannelAccount extends CommonAttributes {
    @Column("service_ch_ac_id")
    private Long id;
    @Column("member_id")
    private Long memberId;
    private String channel;
    @Column("user_id")
    private String userId;
    private String credential;
    @Column("last_access_val_at")
    private String lastAccessValAt;
    @Column("last_access_val_at")
    private String lastAccessSuccessAt;
    @Column("last_access_val_at")
    private String lastAuthFailedAt;
    @Column("offest_mins_to_nxt_login")
    private Long offsetMinToNxtLogin;
    @Column("is_active")
    private Boolean isActive;
    @Column("visibility_scope_id")
    private Long visibilityScopeId;
    @Column("access_validation_code")
    private String accessValidationCode;
}
