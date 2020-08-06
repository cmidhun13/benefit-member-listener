package com.szells.membership.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final String correlationId;
    private final String errMessage;
    private final Integer errCode;

    public AppException(String correlationId, String errMessage, Integer errCode) {
        super(errMessage);
        this.correlationId = correlationId;
        this.errMessage = errMessage;
        this.errCode = errCode;
    }
}
