package com.github.iahrari.orderexample.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceResponse {
    private String source;
    private String destination;
    private Double price;

    private List<String> field;
    private List<String> value;
}
