package com.github.iahrari.orderexample.controller;

import java.util.HashMap;
import java.util.Map;

import com.github.iahrari.orderexample.controller.exception.PriceResponseException;
import com.github.iahrari.orderexample.dto.QueryError;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler(PriceResponseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public QueryError handleQueryError(PriceResponseException ex){
        return ex.getError();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
