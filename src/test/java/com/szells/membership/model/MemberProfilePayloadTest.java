package com.szells.membership.model;

import com.szells.membership.model.payload.MemberProfilePayload;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

@RunWith(SpringRunner.class)
public class MemberProfilePayloadTest {
    private MemberProfilePayload payload;

    public static MemberProfilePayload buildPayload() {
        MemberProfilePayload payload = new MemberProfilePayload();
        payload.setActive(true);
        payload.setCancelledDate(Calendar.getInstance().getTime());
        payload.setCancelledReason("Calkadskd");
        payload.setDateOfBirth(Calendar.getInstance().getTime());
        payload.setEmailAddressPrimary("v@J.com");
        payload.setEndDate(Calendar.getInstance().getTime());
        payload.setExternalMemberReference("ReferenceNumber1223");
        payload.setFirstName("first");
        payload.setGender("M");
        payload.setId("21314324");
        payload.setLanguageId(13L);
        payload.setLastName("last");
        payload.setMailingName1("line1");
        payload.setMailingName2("line2");
        payload.setMemberGuid("guid");
        payload.setMemberJoinDate(Calendar.getInstance().getTime());
        payload.setMemberNumber("23465234");
        payload.setMiddleName("middle");
        payload.setNationalId("134234");
        return payload;
    }

    @Before
    public void setup() {
        this.payload = buildPayload();

    }

    @Test
    public void testMemberProfilePayload() {
        Assert.assertNotNull("init failed!", payload);
        Assert.assertTrue("set active failed", payload.isActive());
    }
}
