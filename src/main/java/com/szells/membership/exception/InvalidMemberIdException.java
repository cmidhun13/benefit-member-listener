package com.szells.membership.exception;

import lombok.Getter;

@Getter
public class InvalidMemberIdException extends Exception {
    private final String correlationId;

    public InvalidMemberIdException(String correlationId) {
        super();
        this.correlationId = correlationId;
    }
}
