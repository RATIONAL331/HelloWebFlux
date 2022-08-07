package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<Flux<UserDto>> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}

	@GetMapping("{id}")
	public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable long id) {
		return userService.getUserByUserId(id)
		                  .map(ResponseEntity::ok)
		                  .defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<UserDto>> createUser(@RequestBody Mono<UserDto> userDtoMono) {
		return userService.saveUser(userDtoMono)
		                  .map(ResponseEntity::ok);
	}

	@PutMapping("{id}")
	public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable long id, @RequestBody Mono<UserDto> userDtoMono) {
		return userService.updateUser(id, userDtoMono)
		                  .map(ResponseEntity::ok)
		                  .defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("{id}")
	public Mono<ResponseEntity<Void>> deleteUser(@PathVariable long id) {
		return userService.deleteUser(id)
		                  .map(ResponseEntity::ok);
	}
}
