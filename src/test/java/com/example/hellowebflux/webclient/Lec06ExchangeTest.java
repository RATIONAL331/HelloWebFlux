package com.example.hellowebflux.webclient;

import com.example.hellowebflux.dto.InputFailedValidationResponse;
import com.example.hellowebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lec06ExchangeTest {
    @Autowired
    private WebClient webClient;

    // exchange = retrieve + additional info http status code
    @Test
    public void stepVerifierTest() {
        webClient.get()
                 .uri("http://localhost:8080/reactive-math/square/{input}/throw", 5)
                 .exchangeToMono(this::exchange)
                 .doOnNext(System.out::println)
                 .doOnError(System.out::println)
                 .as(StepVerifier::create)
                 .expectNextCount(1)
                 .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        if (clientResponse.statusCode() == HttpStatus.BAD_REQUEST) {
            return clientResponse.bodyToMono(InputFailedValidationResponse.class);
        } else {
            return clientResponse.bodyToMono(Response.class);
        }
    }
}
