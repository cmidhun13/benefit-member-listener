package com.szells.membership.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class ServiceAccount {

    private String channel;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("current_credential")
    private String currentCredential;
    private String credential;

    @JsonProperty("access_validation_code")
    private String accessValidationCode;

    @JsonProperty("last_access_val_at")
    private String lastAccessValAt;

    @JsonProperty("last_access_success_at")
    private String lastAccessSuccessAt;

    @JsonProperty("last_auth_failed_at")
    private String lastAuthFailedAt;

    @JsonProperty("offset_mins_to_nxt_login")
    private long nextLoginOffsetMin;

    private MemberAttributeBean.AuditBean auditBean;


    private String preferences;
    @JsonProperty("is_active")
    private boolean isActive;

    private List<AttributeBean> attributes;
}
