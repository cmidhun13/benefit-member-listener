package com.szells.membership.repository;

import com.szells.membership.entity.MembershipBenefitSubscription;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface IMembershipSubscriptionRepository extends ReactiveCrudRepository<MembershipBenefitSubscription, Long> {

    @Query("Update membership_benefit_subscription set is_active= false, cancelled_reason =:cancelledReason," +
            "cancelled_date =:cancelledDate, updated_by =:updatedBy, updated_date =:updated_date, status =:status," +
            "correlation_id =:correlationId where member_id =:memberId and visibility_scope_id =:visibilityScopeId")
    Mono<Integer> deactivateSubscriptionStatusForMember(Long memberId, Long visibilityScopeId, String cancelledReason,
                                                        Instant cancelledDate, String updatedBy, Instant updatedDate,
                                                        String status, String correlationId);

    @Query("Update membership_benefit_subscription set is_active =:isActive, cancelled_date= now() where membership_id =:membershipId")
    Mono<Integer> updateMembership(Long membershipId, Boolean isActive);
}
