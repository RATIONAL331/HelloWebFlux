package com.example.hellowebflux.webclient;

import com.example.hellowebflux.dto.MultiplyRequestDto;
import com.example.hellowebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lec08AttributesTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest() {
        webClient.post()
                 .uri("http://localhost:8080/reactive-math//multiply")
                 .bodyValue(buildRequestDto(3, 5))
                 // NO HEADER => {accept-encoding=gzip, user-agent=ReactorNetty/1.0.21, host=localhost:8080, accept=*/*, Content-Type=application/json, content-length=22}
//                 .attribute("auth", "basic")
                 // {accept-encoding=gzip, user-agent=ReactorNetty/1.0.21, host=localhost:8080, accept=*/*, Authorization=Basic dXNlcjpwYXNz, Content-Type=application/json, content-length=22}
//                 .attribute("auth", "oauth")
                 // {accept-encoding=gzip, user-agent=ReactorNetty/1.0.21, host=localhost:8080, accept=*/*, Authorization=Bearer OAuth-Token, Content-Type=application/json, content-length=22}
                 .retrieve()
                 .bodyToMono(Response.class)
                 .doOnNext(System.out::println)
                 .as(StepVerifier::create)
                 .expectNextMatches(response -> response.getOutput() == 15)
                 .verifyComplete();

    }

    private MultiplyRequestDto buildRequestDto(int a, int b) {
        MultiplyRequestDto multiplyRequestDto = new MultiplyRequestDto();
        multiplyRequestDto.setFirst(a);
        multiplyRequestDto.setSecond(b);
        return multiplyRequestDto;
    }
}
