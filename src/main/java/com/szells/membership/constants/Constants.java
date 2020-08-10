package com.szells.membership.constants;

public class Constants {

    public static final String EXTERNAL_MEMBER_REF_NO = "ExternalMemberRef";
    public static final String DOB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String PROMOTIONAL_STR = "PROMOTIONAL";
    public static final String SMS_STR = "SMS";
    public static final String EMAIL_STR = "EMAIL";
    public static final String HARD_LETTER_STR = "HARD LETTER";
    public static final String PHONE_CALL_STR = "PHONE CALL";
    public static final String IS_PRIMARY = "is_primary";
    public static final String TRUE = "true";
    public static final String MEMBER_SUBSCRIPTION_HISTORY = "member_subscription_history";
    public static final String BATCH = "Batch";
    public static final String X_VISIBILITY_SCOPE_KEY = "X-Visibility-Scope-Key";
    public static final String MEMBER_ID = "memberId";
    public static final String ACTIVATION_CD = "activationCd";
    public static final String MEMBERSHIP_ID = "membershipId";
    public static final String CORRELATION_ID = "correlationId";
    public static final String ID_TYPE = "idType";
    //Membership status
    public static final String SUSPEND = "Suspend";
    public static final String ENROLLED = "Enrolled";

    // Topic Names
    public static final String MEMBERSHIP_CREATE_TOPIC_NAME = "membership.create";
    public static final String MEMBERSHIP_DELETE_TOPIC_NAME = "membership.delete";
    public static final String MEMBERSHIP_UPDATE_TOPIC_NAME = "membership.update";
    //TODO reate topic in kafka (Topic)
    public static final String MEMBER_ACTIVATE_TOPIC_NAME = "member.activate";
    public static final String MEMBER_SUSPEND_TOPIC_NAME = "member.suspend";
    public static final String MEMBER_BULK_UPLOAD_TOPIC_NAME = "member.bulkupload";
    //    public static final String MEMBER_ACTIVATE_TOPIC_NAME = "membership.update"; //TODO once new topic has been created will need to change this to member.activate.
    public static final String MEMBER_CREATE_TOPIC_NAME = "member.create";
    public static final String MEMBER_UPDATE_TOPIC_NAME = "member.update";
    /*
    member.activity
    member.create
    member.delete
    member.update
    membership.create
    membership.delete
    membership.update*/

    // Event Types
    public static final String DELETE_TYPE = "deleteType";
    public static final String DEACTIVATE_TYPE = "deactivateType";
    public static final String CREATE_TYPE = "createType";
    public static final String UPDATE_TYPE = "updateType";

    // Operations
    public static final String DELETE_MEMBER_BY_ID = "deleteMemberById";
    public static final String UPDATE_MEMBER_PROFILE = "updateMembersProfile";
    public static final String CREATE_MEMBER_PROFILE = "createMembersProfile";
    public static final String DEACTIVATE_MEMBERSHIP = "deactivateMembership";
    public static final String CREATE_MEMBERSHIP = "createMembership";
    public static final String ACTIVATE_MEMBER = "activateMember";
    public static final String SUSPEND_MEMBER = "suspendMember";
    public static final String BULKUPLOAD_MEMBER = "bulkUploadMember";
    public static final String CREATE_MEMBER = "createMember";
    public static final String ONBOARD_MEMBER_MEMBERSHIP = "onboardMemberMermbership";
    public static final String ENROLL_MEMBER = "enrollMember";
    public static final String UPDATE_MEMBER = "updateMember";

    public static final String BRMS_TOKEN_KEY = "X-BRMS-Token-Key";
    public static final String ACTIVATION_LIGHT_FAMILIES = "Activation_Light_Families";
    public static final String LIGHT_FAMILIES_ACTIVATED = "Light_Families_Activated";
    public static final String ON = "ON";
    public static final String MEMBERSHIP_STATUS = "Membership_status";
    public static final String DEFAULT_STATUS = "DEFAULT_STATUS";
    public static final String CANCELLED = "CANCELLED";
    public static final String NEW = "NEW";
    public static final String SOLICITATION_ID = "Solicitation_Id";
    public static final String DEFAULT_CANCELLED_STATUS = "104";
    public static final String DEFAULT_NEW_STATUS = "101";
    public static final String ATTRIBUTE_NAME = "attributeName";
    public static final String ATTRIBUTE_VALUE = "attributeValue";

    private Constants() {

    }
}
