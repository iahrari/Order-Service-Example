package com.github.iahrari.orderexample.dto.converter;

import com.github.iahrari.orderexample.domain.Order;
import com.github.iahrari.orderexample.dto.OrderDTO;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToOrder implements Converter<OrderDTO, Order> {
    @Override
    public Order convert(OrderDTO orderDTO) {
        return Order.builder()
                .destination(orderDTO.getDestination())
                .source(orderDTO.getSource())
                .type(orderDTO.getType())
                .build();
    }
}
