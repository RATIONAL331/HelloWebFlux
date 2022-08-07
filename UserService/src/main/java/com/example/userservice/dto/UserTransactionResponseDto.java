package com.example.userservice.dto;

import com.example.userservice.entity.UserTransaction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserTransactionResponseDto {
	private long userId;
	private int amount;
	private UserTransactionStatus status;

	public static UserTransactionResponseDto toDto(UserTransactionRequestDto requestDto,
	                                               UserTransactionStatus status) {
		UserTransactionResponseDto responseDto = new UserTransactionResponseDto();
		responseDto.setUserId(requestDto.getUserId());
		responseDto.setAmount(requestDto.getAmount());
		responseDto.setStatus(status);
		return responseDto;
	}

	public static UserTransactionResponseDto toDto(UserTransaction entity) {
		UserTransactionResponseDto responseDto = new UserTransactionResponseDto();
		responseDto.setUserId(entity.getUserId());
		responseDto.setAmount(entity.getAmount());
		responseDto.setStatus(UserTransactionStatus.APPROVED);
		return responseDto;
	}
}
