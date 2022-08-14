package com.example.hellowebflux.webclient;

import com.example.hellowebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lec05BadRequestTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void stepVerifierTest() {
        webClient.get()
                 .uri("http://localhost:8080/reactive-math/square/{input}/throw", 5)
                 .retrieve()
                 .bodyToMono(Response.class)
                 .doOnNext(System.out::println)
                 .doOnError(System.out::println)
                 .as(StepVerifier::create)
                 .verifyError(WebClientResponseException.BadRequest.class);

        /**
         * java.lang.AssertionError: expectation "expectNextCount(1)" failed (expected: count = 1; actual: counted = 0;
         * signal: onError(org.springframework.web.reactive.function.client.WebClientResponseException$BadRequest: 400 Bad Request from GET http://localhost:8080/reactive-math/square/5/throw))
         */
    }
}
