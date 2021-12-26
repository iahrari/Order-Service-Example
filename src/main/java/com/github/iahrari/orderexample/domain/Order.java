package com.github.iahrari.orderexample.domain;

import com.github.iahrari.orderexample.utils.IdGenerator;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @Builder.Default
    private Long hashId = IdGenerator.generateId();
    @NotNull
    private String source;
    @NotNull
    private String destination;
    @NotNull
    private BigDecimal price;
    @NotNull
    private OrderType type;
}
