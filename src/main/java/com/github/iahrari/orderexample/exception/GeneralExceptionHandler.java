package com.github.iahrari.orderexample.exception;

import java.util.HashMap;
import java.util.Map;

import com.github.iahrari.orderexample.dto.ResponseError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(PriceResponseException.class)
    public ResponseEntity<ResponseError> handlePricingServiceErrors(PriceResponseException ex){
        return ResponseEntity.badRequest()
                .body(ResponseError.builder()
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ResponseError> handleOrderNotFound(OrderException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseError.builder()
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ResponseError> handleInternalServiceHttpCalls(ResourceAccessException ex){
        return ResponseEntity.badRequest()
                .body(ResponseError.builder()
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .badRequest()
                .body(errors);
    }
}
