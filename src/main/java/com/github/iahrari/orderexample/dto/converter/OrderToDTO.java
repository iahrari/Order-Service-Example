package com.github.iahrari.orderexample.dto.converter;

import com.github.iahrari.orderexample.domain.Order;
import com.github.iahrari.orderexample.dto.OrderDTO;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Synchronized;


@Component
public class OrderToDTO implements Converter<Order, OrderDTO> {

    @Override
    @Synchronized
    public OrderDTO convert(Order source) {
        return OrderDTO.builder()
                .hashId(source.getHashId())
                .source(source.getSource())
                .destination(source.getDestination())
                .price(source.getPrice())
                .type(source.getType())
                .build();
    }
}
