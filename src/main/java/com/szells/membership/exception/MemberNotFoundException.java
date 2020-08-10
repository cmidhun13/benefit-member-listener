package com.szells.membership.exception;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends AppException {

    public MemberNotFoundException(String correlationId) {
        super(correlationId, "No Matching Member found for the given memberId", 404);
    }
}
