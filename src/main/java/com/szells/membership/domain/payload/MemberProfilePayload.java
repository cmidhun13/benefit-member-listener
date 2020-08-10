package com.szells.membership.domain.payload;

import com.szells.membership.constants.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberProfilePayload extends BasePayload {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String idType;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "member number for the member being updated!", example = "1232345345", required = true)
    @JsonProperty("member_number")
    private String memberNumber;

    @Schema(description = "external reference number for the member being updated!", example = "A2436245", required = true)
    @JsonProperty("external_member_ref")
    private String externalMemberReference;

    @Schema(description = "Membership Id for the member being updated!", example = "1232345345", required = true)
    private Long membershipId;

    @Schema(description = "status!", example = "Active", required = false)
    private String status;

    @Schema(description = "Title for the member!", example = "Dr.", required = true)
    private String title;

    @Schema(description = "first name of the member!", example = "Sherlock", required = true)
    @JsonProperty("first_name")
    private String firstName;

    @Schema(description = "middle name of the member!", example = "Rodney", required = false)
    @JsonProperty("middle_name")
    private String middleName;

    @Schema(description = "last name of the member!", example = "Holmes", required = true)
    @JsonProperty("last_name")
    private String lastName;

    @Schema(description = "suffix for the member!", example = "Sr.", required = false)
    private String suffix;

    @Schema(description = "visibility scope id for the client!", example = "21", required = false)
    private long visibilityScope;

    @Schema(description = "date of birth for the member!", example = "2028-10-30", required = true)
    @JsonProperty("date_of_birth")
    @JsonFormat(pattern = Constants.DOB_DATE_FORMAT)
    private Date dateOfBirth;

    @Schema(description = "Gender of the member!", example = "Male", required = true)
    private String gender;

    @Schema(description = "Email Address of the member!", example = "sherlock@szells.com", required = true)
    @JsonProperty("email_address_primary")
    private String emailAddressPrimary;

    @Schema(description = "mobile # of the member!", example = "+1 980 227 0045", required = true)
    @JsonProperty("phone_number_mobile")
    private String phoneNumberMobile;

    @Schema(description = "national id of the member!", example = "198451678", required = true)
    @JsonProperty("national_id")
    private String nationalId;

    @Schema(description = "address!", example = "Sherlock", required = false)
    @JsonProperty("postal_address")
    private AddressBean postalAddress;

    @Schema(description = "Member level attributes for the member!", required = false)
    @JsonIgnore
    @JsonProperty(value = "attributes")
    private List<AttributeBean> attributes;

    @Schema(description = "service accounts for the member!", required = false)
    @JsonProperty("service_accts")
    private List<ServiceAccount> serviceAccounts;

    @Schema(description = "Audit for the event!", required = false)
    @JsonProperty("auditBean")
    private MemberAttributeBean.AuditBean audit;

    @Schema(description = "preferences!", example = "Sherlock", required = false)
    private String preferences;

    @Schema(description = "membership start date", example = "2019-10-31", required = false)
    @JsonProperty("start_date")
    private Date startDate;

    @Schema(description = "end date for the memebership", example = "2019-10-31", required = false)
    @JsonProperty("end_date")
    private Date endDate;

    @Schema(description = "Member active ?", example = "Sherlock", required = true)
    @JsonProperty("is_active")
    private boolean isActive;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("current_subscription")
    private List<CurrentSubscription> currentSubscription;

    @Schema(description = "Member joining date!", example = "2019-10-31", required = true)
    @JsonProperty(value = "member_join_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date memberJoinDate;

    @Schema(description = "mailing name 1", example = "Sherlock Holmes", required = true)
    @JsonProperty("mailing_Name1")
    private String mailingName1;

    @Schema(description = "mailing name 2", example = "Sherlock W", required = true)
    @JsonProperty("mailing_Name2")
    private String mailingName2;

    @Schema(description = "Salutation", example = "Senor!", required = true)
    private String salutation;

    @Schema(description = "is the member primary! Note: A primary member cannot be added to " +
            "an existing membership", example = "false", required = true)
    @JsonProperty("is_primary")
    private boolean isPrimary;

    @Schema(description = "Id indicating the language", example = "2", required = true)
    private Long languageId;

    @Schema(description = "Security Question Id", example = "12", required = true)
    private String securityQuestionId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String poeCommonAbbrId;

    @Schema(description = "Security Answer", example = "Apple & Orange!", required = true)
    private String securityAnswer;

    @Schema(description = "suppress Hard Letter Flag!", example = "true", required = true)
    private boolean suppressHardLetterFlag;

    @Schema(description = "suppress Call Flag!", example = "true", required = true)
    private boolean suppressCallFlag;

    @Schema(description = "suppress Email Flag!", example = "false", required = true)
    private boolean suppressEmailFlag;

    @Schema(description = "suppress sms flag !", example = "false", required = true)
    private boolean suppressSmsFlag;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String visibilityScopeHash;

    @Schema(description = "Member GUID!", example = "1233124234", required = true)
    private String memberGuid;

    @Schema(description = "Date of membership Cancellation!", example = "2018-10-19", required = false)
    @JsonProperty("cancelled_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date cancelledDate;

    @Schema(description = "Reason for membership cancellation!", example = "Member cancelled subscription!", required = false)
    @JsonProperty("cancelled_reason")
    private String cancelledReason;

    @Schema(description = "Re-instate reason!", example = "Member subscribed again!", required = false)
    @JsonProperty("reinstate_reason")
    private String reinstateReason;

    @Schema(description = "member is web enabled ?", example = "true", required = false)
    @JsonProperty("webenabled")
    private Boolean webEnabled;

    @Schema(description = "first name of the member!", example = "Sherlock", required = false, accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("member_attribute")
    private List<MemberAttributeBean> memberAttribute;

    @Schema(description = "Solicitation !", example = "Sherlock", required = false, accessMode = Schema.AccessMode.READ_ONLY)
    private String solicitationRef;
}
