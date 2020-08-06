package com.szells.membership.domain.payload;

import com.szells.membership.domain.ResourceType;
import com.szells.membership.domain.ResponseStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServiceResponse {
    private final String correlationId;
    private final ResourceType resourceType;
    private final ResponseStatus status;
    private final String failureReason;
    private final Integer noOfRows;
}
