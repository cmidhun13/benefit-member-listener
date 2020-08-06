package com.szells.membership.mapper;

import com.szells.membership.domain.MembershipSubscriptionDomain;
import com.szells.membership.domain.payload.MemberProfilePayload;
import com.szells.membership.entity.MembershipBenefitSubscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMembershipSubscriptionMapper {
    MembershipSubscriptionDomain toDomain(MembershipBenefitSubscription entity);

    MembershipBenefitSubscription toEntity(MembershipSubscriptionDomain domain);

    @Mapping(source = "memberProfilePayload.membershipId", target = "membershipId")
    @Mapping(source = "memberProfilePayload.id", target = "memberId")
    @Mapping(source = "solicitationId", target = "benefitKey")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(source = "subscriptionStatus", target = "status")
    @Mapping(target = "startDate", defaultExpression = "java(java.time.Instant.now())")
    @Mapping(source = "memberProfilePayload.visibilityScope", target = "visibilityScopeId")
    @Mapping(source = "memberProfilePayload.audit.createdBy", target = "createdBy")
    @Mapping(source = "memberProfilePayload.audit.createdOn", target = "createdDate", defaultExpression = "java(java.util.Calendar.getInstance().getTime())")
    @Mapping(source = "memberProfilePayload.correlationId", target = "correlationId")
    MembershipSubscriptionDomain toDomain(String subscriptionStatus, String solicitationId, MemberProfilePayload memberProfilePayload);

}
