package com.example.hellowebflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient
                // .create()
                .builder()
//                        .defaultHeaders(h -> h.setBasicAuth("user", "pass"))
                .filter(this::sessionToken)
                .build();
    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction exchange) {
        // auth -> basic or oauth
        ClientRequest clientRequest = request.attribute("auth")
                                             .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                                             .orElse(request);
        return exchange.exchange(clientRequest);
    }

//    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction exchange) {
//        System.out.println("generate session token");
//        ClientRequest clientRequest = ClientRequest.from(request).headers(httpHeaders -> httpHeaders.setBearerAuth("some-jwt")).build();
//        return exchange.exchange(clientRequest);
//
//        // {accept-encoding=gzip, user-agent=ReactorNetty/1.0.21, host=localhost:8080, accept=*/*, someKey=someVal, Authorization=Bearer some-jwt, Content-Type=application/json, content-length=22}
//        // Response(date=2022-07-31T19:44:49.709405, output=15)
//    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request)
                            .headers(httpHeaders -> httpHeaders.setBasicAuth("user", "pass"))
                            .build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request)
                            .headers(httpHeaders -> httpHeaders.setBearerAuth("OAuth-Token"))
                            .build();
    }
}
