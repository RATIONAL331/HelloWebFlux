package com.example.userservice.dto;

import com.example.userservice.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
	private long id;
	private String name;
	private int balance;

	public static User toEntity(UserDto userDto) {
		return User.builder()
		           .name(userDto.getName())
		           .balance(userDto.getBalance())
		           .build();
	}
}
