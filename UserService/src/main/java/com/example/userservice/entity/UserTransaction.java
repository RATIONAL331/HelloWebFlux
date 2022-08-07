package com.example.userservice.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserTransaction {
	@Id
	private int id;
	private int userId;
	private int amount;
	private LocalDateTime transactionDate;
}
