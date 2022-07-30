package com.example.hellowebflux.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Response {
    private final LocalDateTime date = LocalDateTime.now();
    private int output;

    public Response(int output) {
        this.output = output;
    }
}
