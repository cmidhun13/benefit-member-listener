package com.szells.membership.repository;

import com.szells.membership.entity.BenPkgSelectionMapping;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBenPkgSelectionMappingRepository extends ReactiveCrudRepository<BenPkgSelectionMapping, Long> {
    //    @Query("select exists (select 1 from ben_pkg_selection_mapping ben, membership_benefit_subscription mem where mem.member_id =:memberId and mem.membership_id =:membershipId and mem.benefit_key IN (:packageBenefits) and ben.mem_sub_flx_pkg_dtl_id=:packageId)")
    @Query("select exists (select 1 from ben_pkg_selection_mapping ben, membership_benefit_subscription mem where mem.member_id =:memberId and mem.membership_id =:membershipId and mem.benefit_key IN (:packageBenefits) and ben.mem_sub_flx_pkg_dtl_id=:packageId and mem.is_active is true)")
    Mono<Boolean> checkIfPackageEnrolled(Long memberId, Long membershipId, List<String> packageBenefits, Long packageId);
}
