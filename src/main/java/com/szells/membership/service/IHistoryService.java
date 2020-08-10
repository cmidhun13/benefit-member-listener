package com.szells.membership.service;

import com.szells.membership.domain.MemberDomain;
import com.szells.membership.domain.payload.MembershipPayload;

public interface IHistoryService {
    void publishForCreateMembership(MembershipPayload membershipPayload);

    void publishForMember(MemberDomain memberDomain);
}
