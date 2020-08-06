package com.szells.membership.domain.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MembershipPayload extends BasePayload {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @Schema(description = "membership_number", example = "123345245", required = false)
    @JsonProperty("membership_number")
    private String membershipNumber;

    @Schema(description = "membership Status", example = "Active", required = false)
    private String status;


    @Schema(description = "membership start date", example = "2019-10-31", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("start_date")
    private Date startDate;

    @Schema(description = "membership end date", example = "2019-10-31", required = false)
    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date endDate;

    @Schema(description = "membership active ?", example = "false", required = false)
    @JsonProperty("is_active")
    private boolean isActive;

    private long requestedTime;

    @Schema(description = "membership GUID", example = "1244234523", required = true)
    private String membershipGUId;

    @Schema(description = "member group type", example = "joint", required = true)
    @JsonProperty(value = "member_group_type")
    private String memberGroupType;

    @Schema(description = "Rule Engine validation required", example = "true", required = false)
    @JsonIgnore
    private boolean ruleEngineRequired;

    @Schema(description = "send correspondence flag", example = "true", required = true)
    @JsonProperty("send_correspondence_flag")
    private boolean sendCorrespondenceFlag;

    @Schema(description = "membership solicitation id", example = "1224334", required = true)
    @JsonProperty(value = "solicitation_id")
    private int solicitationId;

    @Schema(description = "membership Customer id", example = "1224334", required = true)
    @JsonProperty(value = "customer_id")
    private int customerId;

    @JsonProperty(value = "source_context")
    private SourceContextPayload sourceContext;

    @JsonProperty(value = "members")
    private List<MemberProfilePayload> members;

    private List<AttributeBean> attributes;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("cancelled_reason")
    private String cancelledReason;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("reinstate_reason")
    private String reinstateReason;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("tier_change_reason")
    private String tierChangeReason;

    @Schema(description = "Limited disclosure flag", example = "true", required = true)
    @JsonProperty("limited_disclosure_fl")
    private Boolean limitedDisclosureFlag;

}
