package com.example.hellowebflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lec07QueryParamsTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void stepVerifierTest() {
        Map<String, Integer> param = Map.of(
                "count", 10,
                "page", 20
        );

        webClient.get()
//                 .uri(UriComponentsBuilder.fromUriString("http://localhost:8080/jobs/search?count={count}&page={page}")
//                                          .build(10, 20))
//                 .uri(builder -> builder.path("localhost:8080/jobs/search")
//                                        .query("count={count}&page={page}")
//                                        .build(10, 20))
                 .uri(builder -> builder.path("localhost:8080/jobs/search")
                                        .query("count={count}&page={page}")
                                        .build(param))
                 .retrieve()
                 .bodyToFlux(Integer.class)
                 .doOnNext(System.out::println)
                 .as(StepVerifier::create)
                 .expectNextMatches(res -> res == 10)
                 .expectNextMatches(res -> res == 20)
                 .verifyComplete();
    }
}
