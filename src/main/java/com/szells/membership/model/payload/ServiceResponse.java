package com.szells.membership.model.payload;

import com.szells.membership.model.ResourceType;
import com.szells.membership.model.ResponseStatus;
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
