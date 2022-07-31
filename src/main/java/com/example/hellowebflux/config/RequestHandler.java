package com.example.hellowebflux.config;

import com.example.hellowebflux.dto.MultiplyRequestDto;
import com.example.hellowebflux.dto.Response;
import com.example.hellowebflux.exception.InputValidationException;
import com.example.hellowebflux.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {
    private final ReactiveMathService mathService;

    public Mono<ServerResponse> squareHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Mono<Response> responseMono = mathService.findSquare2(input);

//        Mono<ServerResponse> serverResponseMono = ServerResponse.ok().bodyValue("1234"); <- bodyValue arg just Object not publisher

        return ServerResponse.ok()
                             .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable2(input);

        return ServerResponse.ok()
                             .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> tableStreamHandler(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        Flux<Response> responseFlux = mathService.multiplicationTable2(input);

        return ServerResponse.ok()
                             .contentType(MediaType.TEXT_EVENT_STREAM)
                             .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest request) {
        Mono<MultiplyRequestDto> requestDtoMono = request.bodyToMono(MultiplyRequestDto.class);
        Mono<Response> responseMono = mathService.multiply(requestDtoMono);

        return ServerResponse.ok()
                             .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest request) {
        int input = Integer.parseInt(request.pathVariable("input"));
        if (input < 10 || input > 20) {
//            InputFailedValidationResponse inputFailedValidationResponse = new InputFailedValidationResponse();
//            return ServerResponse.badRequest()
//                                 .bodyValue(inputFailedValidationResponse);
            return Mono.error(new InputValidationException(input));
        }
        Mono<Response> responseMono = mathService.findSquare2(input);

        return ServerResponse.ok()
                             .body(responseMono, Response.class);
    }
}
