package com.szells.membership.mapper;

import com.szells.membership.domain.payload.MembershipPayload;
import com.szells.membership.entity.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IMembershipMapper {

    @Mapping(target = "startDate", source = "startDate", defaultExpression = "java(java.time.Instant.now())")
    @Mapping(target = "endDate")
    @Mapping(target = "createdBy", constant = "admin")
    @Mapping(target = "createdDate", defaultExpression = "java(java.time.Instant.now())")
    @Mapping(target = "memberGroupType", source = "memberGroupType",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "status", target = "status")
    @Mapping(source = "active", target = "isActive")
        //@Mapping(source = "id", target = "membershipId")
    Membership toEntity(MembershipPayload domain);

}
