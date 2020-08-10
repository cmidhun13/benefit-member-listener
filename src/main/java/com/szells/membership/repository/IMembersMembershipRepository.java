package com.szells.membership.repository;

import com.szells.membership.entity.MemberMembership;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;

public interface IMembersMembershipRepository extends ReactiveCrudRepository<MemberMembership, Long> {

    @Query("Select * from membership_member where member_id =:memberId and visibilityScope_id=:visibilityScopeId and " +
            "is_active =true and is_primary = true")
    Mono<MemberMembership> findActiveMembershipByMemberId(Long memberId, Long visibilityScopeId);

    @Query("Update membership_member set is_active = :isActive, cancelled_reason= :cancellationReason, " +
            "cancelled_date= :cancelledDate, updated_by= :updatedBy, updated_date= :updatedDate, correlationId= :correlationId " +
            "where member_id =:memberId and visibilityScope_id= :visibilityScopeId  and " +
            "( :isH2NG = null or is_active =:isH2NG ) ")
    Mono<Integer> updateMembershipStatus(Long memberId, Long visibilityScopeId, Boolean isH2NG,
                                         String cancellationReason, Date cancelledDate, String updatedBy,
                                         Boolean isActive,
                                         Instant updatedDate, String correlationId);

}
