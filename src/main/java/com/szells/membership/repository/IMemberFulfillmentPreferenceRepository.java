package com.szells.membership.repository;

import com.szells.membership.entity.MemberFulfillmentPreference;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IMemberFulfillmentPreferenceRepository extends ReactiveCrudRepository<MemberFulfillmentPreference, Long> {

    @Query("SELECT * FROM public.member_fulflmnt_preference where member_id=:memberId " +
            "and comm_category=:commCategory and fulfillment_type=:fulfillmentType")
    Mono<MemberFulfillmentPreference> findByMemberIdAndCommCategoryAndFulfillmentType(Long memberId, String commCategory, String fulfillmentType);
}
