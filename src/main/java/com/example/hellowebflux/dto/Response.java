package com.example.hellowebflux.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Response {
    private final LocalDateTime date = LocalDateTime.now();
    private int output;

    public Response(int output) {
        this.output = output;
    }
}
