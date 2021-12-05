package com.github.iahrari.orderexample.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QueryError {
    private final List<String> fields;
    private final List<String> values;
}
