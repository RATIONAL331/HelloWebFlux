package com.example.userservice.dto;

import com.example.userservice.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@ToString
public class UserDto {
	private int id;
	private String name;
	private int balance;

	public static User toEntity(UserDto userDto) {
		User userEntity = new User();
		BeanUtils.copyProperties(userDto, userEntity);
		return userEntity;
	}
}
