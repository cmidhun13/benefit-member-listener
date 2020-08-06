package com.szells.membership.mapper;

import com.szells.membership.domain.MemberMembershipDomain;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.membership.entity.MemberMembership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMemberMembershipMapper {
    MemberMembershipDomain toDomain(MemberMembership entity);

    MemberMembership toEntity(MemberMembershipDomain domain);

    @Mapping(source = "id", target = "memberId")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdBy", constant = "audit.createdBy")
    @Mapping(target = "createdDate", source = "audit.createdOn", defaultExpression = "java(java.time.Instant.now())")
    @Mapping(target = "visibilityScopeId", source = "visibilityScopeId")
    @Mapping(target = "memberJoinDate", source = "startDate")
    @Mapping(target = "cancelledDate", ignore = true)
    @Mapping(target = "cancelledReason", ignore = true)
    @Mapping(target = "reinstateReason", ignore = true)
    @Mapping(target = "webEnabled", ignore = true)
    MemberMembership buildMemberMembership(MemberProfilePayload payload);
}
