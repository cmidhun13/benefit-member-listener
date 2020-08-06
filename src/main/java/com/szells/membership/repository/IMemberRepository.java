package com.szells.membership.repository;

import com.szells.membership.entity.Member;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

import java.time.Instant;

public interface IMemberRepository extends ReactiveCrudRepository<Member, Long> {

    @Query("Select member_id FROM member where ext_member_ref=:extMemberRef and visibility_scope_id=:visibilityScopeId")
    Mono<Long> findIdByExtMemberRef(String extMemberRef, Long visibilityScopeId);

    @Query("SELECT * FROM member WHERE member_id=:memberId and is_active = true and visibility_scope_id=:visibilityScopeId")
    Mono<Member> findActiveMember(Long memberId, Long visibilityScopeId);

    @Query("SELECT * FROM member WHERE member_id=:memberId and visibility_scope_id=:visibilityScopeId " +
            "and (:isActive = null or is_active = :isActive) ")
    Mono<Member> findMemberById(Long memberId, Long visibilityScopeId, @Nullable Boolean isActive);

    @Query("Update member set is_active =false, end_date =:currentTime, updated_date =:currentTime, " +
            "updated_by =:updatedBy, correlation_id =:correlationId " +
            "where member_id =: memberId and visibilityScopeId =:visibilityScopeId")
    Mono<Integer> deactivateMember(Long memberId, Long visibilityScopeId, Instant currentTime, String updatedBy, String correlationId);

    /*@Query("SELECT * FROM member WHERE email_id =:emailId and activation_cd =:activationCd")
    Mono<Member> findMemberByEmailIdByActivationCd(String emailId, String activationCd);*/
    @Query("SELECT * FROM member WHERE email_id =:emailId")
    Mono<Member> findMemberByEmailIdByActivationCd(String emailId);

    @Query("select member_number from member where member_id=:memberId")
    Mono<String> getMemberNumber(Long memberId);

    @Query("select exists(select 1 from member where member_id=:memberId)")
    Mono<Boolean> checkMemberIDExists(Long memberId);

    @Query("select * from member where member_id=:memberId")
    Mono<Member> findMemberByMemberID(Long memberId);

    @Query("select * from member where member_id=:memberId OR hash_cd=coalesce(TRIM(:hashCd), '')")
    Mono<Member> findMemberByMemberIDByHashCD(Long memberId, String hashCd);
}
