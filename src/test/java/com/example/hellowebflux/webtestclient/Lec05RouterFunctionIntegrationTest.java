package com.example.hellowebflux.webtestclient;

import com.example.hellowebflux.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec05RouterFunctionIntegrationTest {
	private WebTestClient webClient;

	@BeforeAll
	public void setWebClient() {
		this.webClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
	}

	@Test
	public void run() {

	}

	@Test
	public void router1() {
		webClient.get()
		         .uri("/router1/square/{input}", 15)
		         .exchange()
		         .expectStatus()
		         .is2xxSuccessful()
		         .expectBody(Response.class)
		         .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));
	}

	@Test
	public void router2() {
		webClient.get()
		         .uri("/router2/square/{input}", 15)
		         .exchange()
		         .expectStatus()
		         .is2xxSuccessful()
		         .expectBody(Response.class)
		         .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));
	}
}
