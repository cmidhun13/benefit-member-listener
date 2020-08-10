package com.szells.membership.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateMemberPayload extends BasePayload {
    private String activationcd;
    private String emailAddress;
    private String correlationId;
//    private String id;
}
