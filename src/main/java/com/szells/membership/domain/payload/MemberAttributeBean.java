package com.szells.membership.domain.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberAttributeBean {

    @JsonProperty("attribute_name")
    private String name;

    @JsonProperty("attribute_value")
    private String value;

    @Getter
    @Setter
    public static class AuditBean {

        @Schema(description = "Who created this request. required for create operations", example = "Helix UI", required = false)
        @JsonProperty("created_by")
        private String createdBy;

        @Schema(description = "date on which create is initiated", example = "2018-10-29", required = false)
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @JsonProperty("created_on")
        private Date createdOn;

        @Schema(description = "Who created this request. required for update/delete operations", example = "Helix UI", required = false)
        @JsonProperty("updated_by")
        private String updatedBy;

        @Schema(description = "date on which updated is initiated", example = "2018-10-29", required = false)
        @JsonProperty("updated_on")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Date updatedOn;
    }
}
