package com.szells.membership.service;

import com.szells.membership.model.MemberDomain;
import com.szells.membership.model.payload.MembershipPayload;

public interface IHistoryService {
    void publishForCreateMembership(MembershipPayload membershipPayload);

    void publishForMember(MemberDomain memberDomain);
}
