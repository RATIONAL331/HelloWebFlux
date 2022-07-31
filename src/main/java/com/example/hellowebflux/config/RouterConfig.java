package com.example.hellowebflux.config;

import com.example.hellowebflux.dto.InputFailedValidationResponse;
import com.example.hellowebflux.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                              .path("router2", this::serverResponseRouterFunction2)
                              .build();
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction1() {
        return RouterFunctions.route()
                              .GET("router1/square/{input}", requestHandler::squareHandler)
                              .GET("router1/table/{input}", requestHandler::tableHandler)
                              .GET("router1/table/{input}/stream", requestHandler::tableStreamHandler)
                              .POST("router1/multiply", requestHandler::multiplyHandler)
                              .GET("router1/square/{input}/validation", requestHandler::squareHandlerWithValidation)
                              .onError(InputValidationException.class, exceptionHandler())
                              .build();
    }

    //@Bean <- DON'T USE!!!! When you register this bean PATH: [/square/3] work! (It is not intended to be used)
    private RouterFunction<ServerResponse> serverResponseRouterFunction2() {
        return RouterFunctions.route()
                              .GET("square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")), requestHandler::squareHandler) // router2/square/{input}(10..20)
                              .GET("square/{input}", req -> ServerResponse.badRequest().bodyValue("only 10-19 allowed"))
                              .GET("table/{input}", requestHandler::tableHandler) // router2/table/{input}
                              .GET("table/{input}/stream", requestHandler::tableStreamHandler) // router2/table/{input}/stream
                              .POST("multiply", requestHandler::multiplyHandler) // router2/multiply
                              .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation) // router2/square/{input}/validation
                              .onError(InputValidationException.class, exceptionHandler())
                              .build();
    }


    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse inputFailedValidationResponse = new InputFailedValidationResponse();
            inputFailedValidationResponse.setInput(ex.getInput());
            inputFailedValidationResponse.setMessage(ex.getMessage());
            inputFailedValidationResponse.setErrorCode(ex.getErrorCode());
            return ServerResponse.badRequest().bodyValue(inputFailedValidationResponse);
        };
    }
}
