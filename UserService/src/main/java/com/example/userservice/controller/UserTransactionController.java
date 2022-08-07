package com.example.userservice.controller;

import com.example.userservice.dto.UserTransactionRequestDto;
import com.example.userservice.dto.UserTransactionResponseDto;
import com.example.userservice.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/transaction")
public class UserTransactionController {
	private final UserTransactionService userTransactionService;

	@PostMapping
	public Mono<UserTransactionResponseDto> crateTransaction(@RequestBody Mono<UserTransactionRequestDto> requestDtoMono) {
		return requestDtoMono.flatMap(userTransactionService::createTransaction);
	}

	@GetMapping
	public ResponseEntity<Flux<UserTransactionResponseDto>> getAllTransactionsByUserId(@RequestParam long userId) {
		return ResponseEntity.ok(userTransactionService.getAllTransactionsByUserId(userId));
	}
}
