package com.szells.membership.mapper;

import com.szells.membership.model.MemberFulfillmentPref;
import com.szells.membership.model.MemberFulfillmentPrefPayload;
import com.szells.membership.entity.MemberFulfillmentPreference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IFulfillmentPreferenceMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "memberId", target = "memberId")
    @Mapping(source = "membershipId", target = "membershipId")
    @Mapping(source = "commCategory", target = "commCategory")
    @Mapping(source = "fulfillmentType", target = "fulfillmentType")
    @Mapping(source = "commPreferenceSource", target = "commPreferenceSource")
    @Mapping(source = "optOutFlag", target = "optOutFlag")
    @Mapping(source = "optOutStart", target = "optOutStart")
    @Mapping(source = "optOutEnd", target = "optOutEnd")
    @Mapping(target = "createdDate", defaultExpression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedDate", defaultExpression = "java(java.time.Instant.now())")
    MemberFulfillmentPreference toEntity(MemberFulfillmentPref domain);


    MemberFulfillmentPref toDomain(MemberFulfillmentPreference entity);

    @Mapping(source = "audit.createdBy", target = "createdBy")
    @Mapping(source = "audit.createdOn", target = "createdDate")
    @Mapping(source = "audit.updatedBy", target = "updatedBy")
    @Mapping(source = "audit.updatedOn", target = "updatedDate")
    @Mapping(source = "otpOutFl", target = "optOutFlag")
    void toDomain(@MappingTarget MemberFulfillmentPref pref,
                  MemberFulfillmentPrefPayload payload);

}
