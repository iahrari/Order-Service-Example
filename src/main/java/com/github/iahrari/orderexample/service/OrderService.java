package com.github.iahrari.orderexample.service;

import java.util.List;

import com.github.iahrari.orderexample.dto.OrderDTO;

import org.springframework.web.bind.MethodArgumentNotValidException;

public interface OrderService {
    OrderDTO saveOrder(OrderDTO orderDTO) 
        throws MethodArgumentNotValidException;
    List<OrderDTO> getAllOrders();
}
