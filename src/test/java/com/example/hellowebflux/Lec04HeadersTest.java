package com.example.hellowebflux;


import com.example.hellowebflux.dto.MultiplyRequestDto;
import com.example.hellowebflux.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class Lec04HeadersTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void headersTest() {
        webClient.post()
                 .uri("http://localhost:8080/reactive-math//multiply")
                 .bodyValue(buildRequestDto(3, 5))
                 .headers(httpHeaders -> httpHeaders.set("someKey", "someVal"))
                 // {accept-encoding=gzip, user-agent=ReactorNetty/1.0.21, host=localhost:8080, accept=*/*, someKey=someVal, Content-Type=application/json, content-length=22}
//                 .headers(httpHeaders -> httpHeaders.setBasicAuth("user", "pass"))
                 // {accept-encoding=gzip, user-agent=ReactorNetty/1.0.21, host=localhost:8080, accept=*/*, Authorization=Basic dXNlcjpwYXNz, Content-Type=application/json, content-length=22}
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
