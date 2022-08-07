package com.example.userservice.entity;

import com.example.userservice.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@Table("users")
public class User {
	@Id
	private int id;
	private String name;
	private int balance;

	public static UserDto toDto(User userEntity) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}
}
