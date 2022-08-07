package com.example.userservice.repository;

import com.example.userservice.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
	@Modifying
	@Query("UPDATE users " +
			"SET balance = balance - :amount " +
			"WHERE id = :userId " +
			"AND balance >= :amount")
	Mono<Boolean> updateUserBalance(long userId, int amount);
}
