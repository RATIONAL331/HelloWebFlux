package com.example.hellowebflux;

import com.example.hellowebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lec01GetSingleResponseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest() {
        Response response = webClient.get()
                                     .uri("http://localhost:8080/reactive-math/square1/{number}", 5)
                                     .retrieve()
                                     .bodyToMono(Response.class) // Mono<Response>
                                     .block();
        System.out.println(response);
    }

    @Test
    public void stepVerifierTest() {
        webClient.get()
                 .uri("http://localhost:8080/reactive-math/square1/{number}", 5)
                 .retrieve()
                 .bodyToMono(Response.class)
                 .as(StepVerifier::create)
                 .expectNextMatches(res -> res.getOutput() == 25)
                 .verifyComplete();
    }
}
