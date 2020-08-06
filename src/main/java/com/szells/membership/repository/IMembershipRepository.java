package com.szells.membership.repository;

import com.szells.membership.entity.Membership;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IMembershipRepository extends ReactiveCrudRepository<Membership, Long> {

    @Query("Update membership set is_active =:isActive,status =:status where membership_id =:membershipId")
    Mono<Integer> updateMembershipStatus(Long membershipId, Boolean isActive, String status);

    @Query("Update membership set status =:status, is_active =:isActive where membership_number =:membershipNumber and customer_id=:customerId")
    Mono<Integer> updateMembershipStatus(String status, Boolean isActive, String membershipNumber,Integer customerId);

    @Query("select mem.membership_id as membership_id from membership mem, member me where me.member_number = mem.membership_number and mem.is_active is true and me.member_id=:memberId and (mem.solicitation_id IS NULL OR  mem.solicitation_id =:solicitationId)")
    Mono<Long> queryMemberShipIdByMemberIdBySolicitationId(Long memberId, Integer solicitationId);
}
