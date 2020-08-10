package com.szells.membership.exception;

import lombok.Getter;

@Getter
public class MemberSaveException extends AppException {
    public MemberSaveException(String correlationId) {
        super(correlationId, "An Error occurred while submitting Record for a save!", 500);
    }
}
