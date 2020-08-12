package com.szells.membership.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelRequestPayload extends BasePayload {

    @Schema(description = "reason for deleting the member.", example = "Member cancelled his subscription", required = true)
    private String deleteReason;

    @Schema(description = "Date when member is deleted.", example = "28-10-2020", required = true)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date cancelDate;

    @Schema(description = "Originating system of the request.", example = "Helix UI", required = false)
    private String sourceSystem;

    @Schema(description = "Who initiated this delete for the member ?.", example = "BATCH, HelixUI etc", required = true)
    @JsonProperty("updated_by")
    private String updatedBy;

    @Schema(description = "Date When member is deleted.", example = "28-10-2020", required = false)
    @JsonProperty("updated_on")
    private Date updatedDate;

    @Schema(description = "Member being deleted!", example = "123345546", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    private Long memberId;

}
