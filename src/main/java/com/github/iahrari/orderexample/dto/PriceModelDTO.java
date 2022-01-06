package com.github.iahrari.orderexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PriceModelDTO {
    private String source;
    private String destination;
    private Double price;
}
