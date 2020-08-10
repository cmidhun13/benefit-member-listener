package com.szells.membership.repository;

import com.szells.membership.entity.SvcChannelAccount;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ISvcChannelAccntRepository extends ReactiveCrudRepository<SvcChannelAccount, Long> {

    @Query("Update service_channel_account set is_active =:isActive, correlation_id =:correlationId where " +
            "member_id =:memberId and visibility_scope_id =:visibilityScopeId")
    Mono<Integer> updateStatusForMemberId(Long memberId, Long visibilityScopeId, Boolean isActive,
                                          String correlationId);
}
