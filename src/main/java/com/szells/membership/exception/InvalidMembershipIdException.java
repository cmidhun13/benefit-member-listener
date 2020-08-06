package com.szells.membership.exception;


import lombok.Getter;

@Getter
public class InvalidMembershipIdException extends AppException {

    public InvalidMembershipIdException(String correlationId) {
        super(correlationId, "No Matching Member found for the given memberId", 404);
    }
}
