package com.example.hellowebflux.exceptionhandler;

import com.example.hellowebflux.dto.InputFailedValidationResponse;
import com.example.hellowebflux.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InputValidationHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleInputValidationException(InputValidationException e) {
        InputFailedValidationResponse inputFailedValidationResponse = new InputFailedValidationResponse();
        inputFailedValidationResponse.setErrorCode(e.getErrorCode());
        inputFailedValidationResponse.setInput(e.getInput());
        inputFailedValidationResponse.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(inputFailedValidationResponse);
    }
}
