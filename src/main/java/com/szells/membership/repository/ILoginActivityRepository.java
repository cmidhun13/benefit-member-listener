package com.szells.membership.repository;

import com.szells.membership.entity.LoginActivity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ILoginActivityRepository extends ReactiveCrudRepository<LoginActivity, Long> {

}
