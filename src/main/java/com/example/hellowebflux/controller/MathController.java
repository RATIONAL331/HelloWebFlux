package com.example.hellowebflux.controller;

import com.example.hellowebflux.dto.Response;
import com.example.hellowebflux.service.MathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/math")
@RequiredArgsConstructor
public class MathController {
    private final MathService mathService;

    @GetMapping("/square/{input}")
    public Response findSquare(@PathVariable("input") int input) {
        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public List<Response> multiplicationTable(@PathVariable("input") int input) {
        return mathService.multiplicationTable(input);
    }
}
