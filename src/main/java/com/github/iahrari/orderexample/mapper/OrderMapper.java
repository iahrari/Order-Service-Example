package com.github.iahrari.orderexample.mapper;

import com.github.iahrari.orderexample.domain.Order;
import com.github.iahrari.orderexample.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDTO orderDTO);
    OrderDTO toDTO(Order order);
}
