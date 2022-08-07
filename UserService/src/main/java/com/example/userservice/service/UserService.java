package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public Flux<UserDto> getAll() {
		return userRepository.findAll().map(User::toDto);
	}

	public Mono<UserDto> getUserByUserId(long userId) {
		return userRepository.findById(userId).map(User::toDto);
	}

	public Mono<UserDto> saveUser(Mono<UserDto> userDtoMono) {
		return userDtoMono.map(UserDto::toEntity)
		                  .flatMap(userRepository::save)
		                  .map(User::toDto);
	}

	public Mono<UserDto> updateUser(long userId, Mono<UserDto> userDtoMono) {
		return userRepository.findById(userId)
		                     // DB에서 구해오고, 업데이트 대상을 엔티티 형태로 고쳐서 다시 저장 (아이디를 같게하면 업데이트)
		                     .flatMap(user -> userDtoMono.map(UserDto::toEntity)
		                                                 .doOnNext(entity -> entity.setId(user.getId())))
		                     .flatMap(userRepository::save)
		                     .map(User::toDto);

	}

	public Mono<Void> deleteUser(long userId) {
		return userRepository.deleteById(userId);
	}
}
