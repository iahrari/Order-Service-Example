package com.github.iahrari.orderexample.domain;

import com.github.iahrari.orderexample.utils.IdGenerator;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @Builder.Default
    private Long hashId = IdGenerator.generateId();

    private String source;
    private String destination;
    private Double price;
    private OrderType type;
}
