package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserTransactionResponseDto {
	private int userId;
	private int amount;
	private UserTransactionStatus status;
}
