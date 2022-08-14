package com.example.hellowebflux.webtestclient;

import com.example.hellowebflux.controller.ReactiveMathValidationController;
import com.example.hellowebflux.dto.Response;
import com.example.hellowebflux.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathValidationController.class)
public class Lec04ErrorHandlingTest {
	@Autowired
	private WebTestClient webClient;

	@MockBean
	private ReactiveMathService reactiveMathService;

	@Test
	public void errorHandling() {
		Mockito.when(reactiveMathService.findSquare2(Mockito.anyInt()))
		       .thenReturn(Mono.just(new Response(1)));

		webClient.get()
		         .uri("/reactive-math/square/{number}/throw", 5)
		         .exchange()
		         .expectStatus()
		         .is4xxClientError()
		         .expectBody()
		         //.isEmpty()
		         .jsonPath("$.message").isEqualTo("Allowed range 10 - 20")
		         .jsonPath("$.errorCode").isEqualTo(100)
		         .jsonPath("$.input").isEqualTo(5);
	}
}
