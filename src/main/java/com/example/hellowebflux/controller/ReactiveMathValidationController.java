package com.example.hellowebflux.controller;

import com.example.hellowebflux.dto.Response;
import com.example.hellowebflux.exception.InputValidationException;
import com.example.hellowebflux.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reactive-math")
public class ReactiveMathValidationController {
    private final ReactiveMathService mathService;

    @GetMapping("/square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable("input") int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }
        return mathService.findSquare2(input);
    }

    @GetMapping("/square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable("input") int input) {
        return Mono.just(input)
                   .handle((i, sink) -> {
                       if (i < 10 || i > 20) {
                           sink.error(new InputValidationException(i));
                       } else {
                           sink.next(i);
                       }
                   })
                   .cast(Integer.class)
                   .flatMap(mathService::findSquare2);
    }

    @GetMapping("/square/{input}/assignment")
    public Mono<ResponseEntity<Response>> assignment(@PathVariable("input") int input) {
        return Mono.just(input)
                   .filter(i -> i >= 10 && i <= 20)
                   .flatMap(mathService::findSquare2)
                   .map(ResponseEntity::ok)
                   .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
