package com.szells.membership.domain.payload;

import lombok.Data;

import java.util.List;

/**
 * @author Riya Patel
 */
@Data
public class SuspendMemberPayload extends BasePayload {
    private List<String> memberId;
    private String customerId;
    private String status;
//    private String id;
}
