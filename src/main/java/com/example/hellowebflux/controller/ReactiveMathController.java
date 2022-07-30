package com.example.hellowebflux.controller;

import com.example.hellowebflux.dto.MultiplyRequestDto;
import com.example.hellowebflux.dto.Response;
import com.example.hellowebflux.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {
    private final ReactiveMathService mathService;

    @GetMapping("/square1/{input}")
    public Mono<Response> findSquare(@PathVariable("input") int input) {
        return mathService.findSquare(input);
    }

    @GetMapping("/square2/{input}")
    public Mono<Response> findSquare2(@PathVariable("input") int input) {
        return mathService.findSquare2(input);
    }

    @GetMapping("/square3/{input}")
    public Mono<Response> findSquare3(@PathVariable("input") int input) {
        return mathService.findSquare3(input);
    }

    @GetMapping("/table1/{input}")
    public Flux<Response> multiplicationTable(@PathVariable("input") int input) {
        /** AbstractJackson2Encoder.class
         * return Flux.from(inputStream).collectList().map((list) -> {  // <- collectList() is blocking
         *     return this.encodeValue(list, bufferFactory, listType, mimeType, hints);
         * }).flux();
         */
        return mathService.multiplicationTable(input);
    }

    @GetMapping(path = "/table1/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable("input") int input) {
        return mathService.multiplicationTable(input);
    }

    @GetMapping("/table2/{input}")
    public Flux<Response> multiplicationTable2(@PathVariable("input") int input) {
        /** AbstractJackson2Encoder.class
         * return Flux.from(inputStream).collectList().map((list) -> {  // <- collectList() is blocking
         *     return this.encodeValue(list, bufferFactory, listType, mimeType, hints);
         * }).flux();
         */
        return mathService.multiplicationTable2(input);
    }

    @GetMapping(path = "/table2/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTable2Stream(@PathVariable("input") int input) {
        return mathService.multiplicationTable2(input);
    }

    @PostMapping("/multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> dtoMono,
                                   @RequestHeader Map<String, String> headers) {
        System.out.println(headers);
        return mathService.multiply(dtoMono);
    }
}
