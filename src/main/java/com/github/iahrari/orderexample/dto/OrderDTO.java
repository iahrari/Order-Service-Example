package com.github.iahrari.orderexample.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.iahrari.orderexample.domain.OrderType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long hashId;

    @NotBlank
    private String source;
    @NotBlank
    private String destination;
    private Double price;

    @NotNull
    private OrderType type;
}
