package com.example.hellowebflux;

import com.example.hellowebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lec02GetMultiResponseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void fluxTest() {
        webClient.get()
                 .uri("http://localhost:8080/reactive-math/table2/{first}", 5)
                 .retrieve()
                 .bodyToFlux(Response.class)
                 .doOnNext(System.out::println)
                 .as(StepVerifier::create)
                 .expectNextCount(10)
                 .verifyComplete();
    }

    @Test
    public void fluxStreamTest() {
        webClient.get()
                 .uri("http://localhost:8080/reactive-math/table2/{first}/stream", 5)
                 .retrieve()
                 .bodyToFlux(Response.class)
                 .doOnNext(System.out::println)
                 .as(StepVerifier::create)
                 .expectNextCount(10)
                 .verifyComplete();
    }
}
