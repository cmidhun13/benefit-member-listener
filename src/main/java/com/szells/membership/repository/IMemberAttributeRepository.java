package com.szells.membership.repository;

import com.szells.membership.entity.MemberAttribute;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IMemberAttributeRepository extends ReactiveCrudRepository<MemberAttribute, Long> {

    @Query("UPDATE member_attribute set is_active= :isActive, correlation_id= :correlationId where member_id= :memberId")
    Mono<Integer> updateStatusForMemberId(Long memberId, Boolean isActive, String correlationId);

}
