package com.szells.membership.domain.request;

import com.szells.membership.domain.payload.BasePayload;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateHashRequest extends BasePayload {
    @JsonProperty("member_id")
    public Long memberid;
    @JsonProperty("hash_cd")
    public String hashCd;
    public Boolean delete_fl;
}
