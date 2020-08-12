package com.szells.membership.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Riya Patel
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberFileData {
    private String emailId;
    private String firstName;
    private String lastName;
    private String addressLine2;
    private String addressLine1;
    private String addressLine3;
    private String city;
    private String state;
    private String country;
    private String pin;
}
