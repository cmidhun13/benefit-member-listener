package com.szells.membership.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class Member extends CommonAttributes {

    @Column("member_id")
    @Id
    private Long memberId;
    @Column("visibility_scope_id")
    private long visibilityScopeId;
    private String title;

    @Column("fname")
    private String firstName;

    @Column("mname")
    private String middleName;

    @Column("lname")
    private String lastName;

    private String suffix;

    private LocalDate dob;
    private String gender;
    @Column("member_number")
    private String memberNumber;
    @Column("email_id")
    private String emailId;

    @Column("phone_nnumber")
    private String phoneNumber;

    @Column("national_id")
    private String nationalId;

    @Column("ext_member_ref")
    private String extMemberRef;

    private String preferences;

    @Column("start_date")
    private Date startDate;

    @Column("end_date")
    private Date endDate;

    @Column("is_active")
    private Boolean isActive;

    @Column("aof_key")
    private String aofKey;

    @Column("mailing_name1")
    private String mailingName1;

    @Column("mailing_name2")
    private String mailingName2;

    private String salutation;

    @Column("member_guid")
    private String memberGUId;

    @Column("language_id")
    private Long languageId;

    @Column("activation_cd")
    private String activationCd;

    @Column("hash_cd")
    private String hash_cd;

    @Column("activate_date")
    private Instant activateDate;
}
