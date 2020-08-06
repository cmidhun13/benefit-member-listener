package com.szells.membership.mapper;

import com.szells.membership.domain.MemberDomain;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.membership.entity.Member;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface IMemberMapper {

    @Mapping(source = "id", target = "memberId")
    @Mapping(source = "visibilityScope", target = "visibilityScopeId")
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(source = "externalMemberReference", target = "extMemberRef")
    @Mapping(source = "phoneNumberMobile", target = "phoneNumber")
    @Mapping(target = "updatedBy", constant = "szells service")
    @Mapping(target = "createdDate", source = "audit.createdOn", defaultExpression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedDate", source = "audit.updatedOn", defaultExpression = "java(java.time.Instant.now())")
    void convertDomainToEntity(MemberProfilePayload memberDomain, @MappingTarget Member member);

    @Mapping(target = "id", source = "memberId")
    @Mapping(target = "visibilityScope", source = "visibilityScopeId")
    @Mapping(target = "dateOfBirth", source = "dob")
    @Mapping(target = "externalMemberReference", source = "extMemberRef")
    @Mapping(target = "phoneNumberMobile", source = "phoneNumber")
    MemberProfilePayload convertEntityToDomain(Member member);

    @Mapping(source = "visibilityScope", target = "visibilityScopeId")
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(source = "externalMemberReference", target = "extMemberRef")
    @Mapping(source = "phoneNumberMobile", target = "phoneNumber")
    @Mapping(target = "createdBy", source = "audit.createdBy")
    @Mapping(target = "createdDate", source = "audit.createdOn", defaultExpression = "java(java.time.Instant.now())")
    Member convertDomainToEntity(MemberProfilePayload member);

    MemberDomain toMemberDomain(Member member);

}
