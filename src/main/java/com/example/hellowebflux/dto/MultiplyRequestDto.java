package com.example.hellowebflux.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MultiplyRequestDto {
    private int first;
    private int second;
}
