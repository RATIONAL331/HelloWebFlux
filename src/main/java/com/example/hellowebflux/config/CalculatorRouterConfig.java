package com.example.hellowebflux.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class CalculatorRouterConfig {
    private final CalculationHandler calculationHandler;

    @Bean
    public RouterFunction<ServerResponse> calculatorRouterFunction() {
        return RouterFunctions.route()
                              .path("assignment", this::calculatorRouterFunctionOp)
                              .build();
    }

    private RouterFunction<ServerResponse> calculatorRouterFunctionOp() {
        return RouterFunctions.route()
                              .GET("{first}/{second}", isOperation("+"), calculationHandler::additionHandler)
                              .GET("{first}/{second}", isOperation("-"), calculationHandler::subtractionHandler)
                              .GET("{first}/{second}", isOperation("*"), calculationHandler::multiplyHandler)
                              .GET("{first}/{second}", isOperation("/"), calculationHandler::divideHandler)
                              .GET("{first}/{second}", req -> ServerResponse.badRequest().bodyValue("Unknown operation"))
                              .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(headers -> ObjectUtils.nullSafeEquals(operation,
                                                                               headers.asHttpHeaders().toSingleValueMap().get("OP")));
    }
}
