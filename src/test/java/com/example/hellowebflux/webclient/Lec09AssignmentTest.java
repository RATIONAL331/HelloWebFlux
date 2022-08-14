package com.example.hellowebflux.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lec09AssignmentTest {
    private static final String FORMAT = "%d %s %d = %s";
    private static final int A = 10;

    @Autowired
    private WebClient webClient;

    @Test
    public void test() {
        Flux.range(1, 5)
            .flatMap(b -> Flux.just("+", "-", "*", "/")
                              .flatMap(op -> sent(b, op)))
            .doOnNext(System.out::println)
            .as(StepVerifier::create)
            .expectNextCount(20)
//                .thenConsumeWhile(Objects::nonNull)
            .verifyComplete();
    }

    private Mono<String> sent(int b, String op) {
        return webClient.get()
                        .uri("http://localhost:8080/assignment/{a}/{b}", A, b)
                        .headers(h -> h.set("OP", op))
                        .retrieve()
                        .bodyToMono(String.class)
                        .map(str -> String.format(FORMAT, A, op, b, str));
    }
}
