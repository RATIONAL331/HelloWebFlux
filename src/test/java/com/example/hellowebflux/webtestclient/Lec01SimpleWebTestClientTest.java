package com.example.hellowebflux.webtestclient;

import com.example.hellowebflux.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class Lec01SimpleWebTestClientTest {
	@Autowired
	private WebTestClient webClient;

	@Test
	public void stepVerifierTest() {
		webClient.get()
		         .uri("http://localhost:8080/reactive-math/square1/{number}", 5)
		         .exchange()
		         .expectStatus()
		         .is2xxSuccessful()
		         .expectHeader()
		         .contentType(MediaType.APPLICATION_JSON)
		         .returnResult(Response.class)
		         .getResponseBody()
		         .as(StepVerifier::create)
		         .expectNextMatches(res -> res.getOutput() == 25)
		         .verifyComplete();
	}

	@Test
	public void fluentAssertions() {
		webClient.get()
		         .uri("http://localhost:8080/reactive-math/square1/{number}", 5)
		         .exchange()
		         .expectStatus()
		         .is2xxSuccessful()
		         .expectHeader()
		         .contentType(MediaType.APPLICATION_JSON)
		         .expectBody(Response.class)
		         .value(res -> Assertions.assertThat(res.getOutput()).isEqualTo(25));
	}
}
