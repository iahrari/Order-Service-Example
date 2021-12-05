package com.github.iahrari.orderexample.controller.exception;

import com.github.iahrari.orderexample.dto.QueryError;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PriceResponseException extends RuntimeException {
    private final QueryError error;
}
