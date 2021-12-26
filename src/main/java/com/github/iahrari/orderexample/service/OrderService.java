package com.github.iahrari.orderexample.service;

import java.util.List;

import com.github.iahrari.orderexample.dto.OrderDTO;

public interface OrderService {
    OrderDTO saveOrder(OrderDTO orderDTO);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrder(Long id);
}
