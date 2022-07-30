package com.example.hellowebflux.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class InputFailedValidationResponse {
    private int errorCode;
    private int input;
    private String message;
}
