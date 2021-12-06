package com.github.iahrari.orderexample.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.iahrari.orderexample.dto.OrderDTO;

import org.springframework.web.bind.MethodArgumentNotValidException;

public interface OrderService {
    OrderDTO saveOrder(OrderDTO orderDTO) 
        throws MethodArgumentNotValidException, JsonMappingException, JsonProcessingException;
    List<OrderDTO> getAllOrders();
}
