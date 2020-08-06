package com.szells.membership.domain.payload;

import lombok.Data;

@Data
public class ActivateMemberPayload extends BasePayload {
    private String activationcd;
    private String emailAddress;
    private String correlationId;
//    private String id;
}
