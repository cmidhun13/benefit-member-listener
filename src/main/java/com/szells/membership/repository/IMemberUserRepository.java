package com.szells.membership.repository;

import com.szells.membership.entity.MemberUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IMemberUserRepository extends ReactiveCrudRepository<MemberUser, String> {

    @Query("SELECT user_id, member_id FROM member_user WHERE member_id=:memberId")
    Mono<MemberUser> findByMemberID(Long memberId);

    @Query("select exists(select 1 from member_user where member_id=:memberId)")
    Mono<Boolean> checkMemberIDExists(Long memberId);
}
