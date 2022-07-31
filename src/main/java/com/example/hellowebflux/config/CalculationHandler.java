package com.example.hellowebflux.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CalculationHandler {

    public Mono<ServerResponse> additionHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divideHandler(ServerRequest serverRequest) {
        return process(serverRequest, (a, b) -> {
            if (b == 0) {
                return ServerResponse.badRequest().bodyValue("Division by zero");
            }
            return ServerResponse.ok().bodyValue(a / b);
        });
    }

    private Mono<ServerResponse> process(ServerRequest serverRequest, BiFunction<Integer, Integer, Mono<ServerResponse>> operation) {
        int first = getValue(serverRequest, "first");
        int second = getValue(serverRequest, "second");
        return operation.apply(first, second);
    }

    private int getValue(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable(key));
    }
}
