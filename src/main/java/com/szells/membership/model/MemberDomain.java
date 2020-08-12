package com.szells.membership.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDomain {

    private long memberId;
    private long visibilityScopeId;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private Date dob;
    private String gender;
    private String memberNumber;
    private String emailId;
    private String phoneNumber;
    private String nationalId;
    private String extMemberRef;
    private String preferences;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;
    private String aofKey;
    private String mailingName1;
    private String mailingName2;
    private String salutation;
    private String memberGUId;
    private Long languageId;
    private String createdBy;
    private Instant createdDate;
    private String updatedBy;
    private Instant updatedDate;
    private String correlationId;
    private String activationCd;
}
