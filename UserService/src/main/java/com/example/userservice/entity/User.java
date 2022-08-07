package com.example.userservice.entity;

import com.example.userservice.dto.UserDto;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@Table("users")
public class User {
	@Id
	private long id;
	private String name;
	private int balance;

	public static UserDto toDto(User userEntity) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}
}
