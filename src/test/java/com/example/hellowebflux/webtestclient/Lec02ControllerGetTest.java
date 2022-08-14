package com.example.hellowebflux.webtestclient;

import com.example.hellowebflux.controller.ParamsController;
import com.example.hellowebflux.controller.ReactiveMathController;
import com.example.hellowebflux.dto.Response;
import com.example.hellowebflux.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
public class Lec02ControllerGetTest {
	@Autowired
	private WebTestClient webClient;

	@MockBean
	private ReactiveMathService reactiveMathService;

	@Test
	public void mocking() {
		Mockito.when(reactiveMathService.findSquare(Mockito.anyInt()))
		       .thenReturn(Mono.just(new Response(25)));

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

	@Test
	public void mockingListResponse() {
		Mockito.when(reactiveMathService.multiplicationTable2(Mockito.anyInt()))
		       .thenReturn(Flux.range(1, 3)
		                       .map(Response::new));

		webClient.get()
		         .uri("http://localhost:8080/reactive-math/table2/{input}", 5)
		         .exchange()
		         .expectStatus()
		         .is2xxSuccessful()
		         .expectHeader()
		         .contentType(MediaType.APPLICATION_JSON)
		         .expectBodyList(Response.class)
		         .hasSize(3);
	}

	@Test
	public void mockingStreamingResponse() {
		Mockito.when(reactiveMathService.multiplicationTable2(Mockito.anyInt()))
		       .thenReturn(Flux.range(1, 3)
		                       .map(Response::new)
		                       .delayElements(Duration.ofMillis(100)));

		webClient.get()
		         .uri("http://localhost:8080/reactive-math/table2/{input}/stream", 5)
		         .exchange()
		         .expectStatus()
		         .is2xxSuccessful()
		         .expectHeader()
		         .contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
		         .expectBodyList(Response.class)
		         .hasSize(3);
	}

	@Test
	public void paramsTest() {
		Map<String, Integer> param = Map.of(
				"count", 10,
				"page", 20
		);

		webClient.get()
		         .uri(builder -> builder.path("/jobs/search") // need to slash append
		                                .query("count={count}&page={page}")
		                                .build(param))
		         .exchange()
		         .expectStatus()
		         .is2xxSuccessful()
		         .expectBodyList(Integer.class)
		         .hasSize(2)
		         .contains(10, 20);
	}

}
