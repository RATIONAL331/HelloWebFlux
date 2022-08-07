package com.example.userservice.service;

import com.example.userservice.dto.UserTransactionRequestDto;
import com.example.userservice.dto.UserTransactionResponseDto;
import com.example.userservice.dto.UserTransactionStatus;
import com.example.userservice.entity.UserTransaction;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.repository.UserTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserTransactionService {
	private final UserRepository userRepository;
	private final UserTransactionRepository userTransactionRepository;

	public Mono<UserTransactionResponseDto> createTransaction(UserTransactionRequestDto requestDto) {
		return userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
		                     .filter(Boolean::booleanValue)
		                     .map(b -> UserTransaction.toEntity(requestDto))
		                     .flatMap(userTransactionRepository::save)
		                     .map(saved -> UserTransactionResponseDto.toDto(requestDto, UserTransactionStatus.APPROVED))
		                     .defaultIfEmpty(UserTransactionResponseDto.toDto(requestDto, UserTransactionStatus.DECLINED));
	}

	public Flux<UserTransactionResponseDto> getAllTransactionsByUserId(long userId) {
		return userTransactionRepository.findAllByUserId(userId)
		                                .map(UserTransactionResponseDto::toDto);
	}
}
