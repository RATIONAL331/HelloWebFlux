package com.example.hellowebflux.service;

import com.example.hellowebflux.dto.MultiplyRequestDto;
import com.example.hellowebflux.dto.Response;
import com.example.hellowebflux.utils.SleepUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ReactiveMathService {
    public Mono<Response> findSquare(int input) {
        return Mono.fromSupplier(() -> new Response(input * input));
    }

    public Mono<Response> findSquare2(int input) {
        return Mono.fromSupplier(() -> input * input)
                   .map(Response::new);
    }

    public Mono<Response> findSquare3(int input) {
        return Mono.just(input * input)
                   .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input) {
        return Flux.range(1, 10)
                   .doOnNext(integer -> SleepUtils.sleepSeconds(1)) // blocking -> when cancel called recognized slow
                   .doOnNext(integer -> System.out.println("### Reactive MathService Processing: " + integer))
                   .map(i -> new Response(input * i));
    }

    public Flux<Response> multiplicationTable2(int input) {
        return Flux.range(1, 10)
                   .delayElements(Duration.ofSeconds(1))
                   .doOnNext(integer -> System.out.println("### Reactive MathService Processing: " + integer))
                   .map(i -> new Response(input * i));
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono) {
        return dtoMono.map(dto -> dto.getFirst() * dto.getSecond())
                      .map(Response::new);
    }
}
