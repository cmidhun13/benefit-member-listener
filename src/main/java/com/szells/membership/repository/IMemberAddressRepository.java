package com.szells.membership.repository;

import com.szells.membership.entity.MemberAddress;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IMemberAddressRepository extends ReactiveCrudRepository<MemberAddress, Long> {

    @Query("select * from member_address where member_id =:memberId limit 1")
    Mono<MemberAddress> findMemberAddressByMemberId(Long memberId);

    @Query("select exists(select 1 from member_address where member_id=:memberId)")
    Mono<Boolean> checkMemberIDExists(Long memberId);
}
