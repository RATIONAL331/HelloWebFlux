package com.example.userservice.entity;

import com.example.userservice.dto.UserTransactionRequestDto;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class UserTransaction {
	@Id
	private long id;
	private long userId;
	private int amount;
	private LocalDateTime transactionDate;

	public static UserTransaction toEntity(UserTransactionRequestDto userTransactionRequestDto) {
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setUserId(userTransactionRequestDto.getUserId());
		userTransaction.setAmount(userTransactionRequestDto.getAmount());
		userTransaction.setTransactionDate(LocalDateTime.now());
		return userTransaction;
	}
}
