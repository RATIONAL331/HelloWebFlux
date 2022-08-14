package com.example.hellowebflux.webtestclient;

import com.example.hellowebflux.config.RequestHandler;
import com.example.hellowebflux.config.RouterConfig;
import com.example.hellowebflux.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lec05RouterFunctionTest {
	private WebTestClient webClient;

	@Autowired
	private RouterConfig config;

	@MockBean
	private RequestHandler requestHandler;

	@BeforeAll
	public void setWebClient() {
		this.webClient = WebTestClient.bindToRouterFunction(config.highLevelRouter()).build();
	}

	@Test
	public void run() {
		Mockito.when(requestHandler.squareHandler(Mockito.any()))
		       .thenReturn(ServerResponse.ok().bodyValue(new Response(225)));
		webClient.get()
		         .uri("/router2/square/{input}", 15)
		         .exchange()
		         .expectStatus()
		         .is2xxSuccessful()
		         .expectBody(Response.class)
		         .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));
	}

}
