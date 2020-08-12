package com.szells.membership.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Riya Patel
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkUploadMember extends BasePayload{
    String FileName;
    String customerId;
    String solicitationId;
    String authorization;
}
