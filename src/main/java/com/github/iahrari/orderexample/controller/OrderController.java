package com.github.iahrari.orderexample.controller;

import java.util.List;

import javax.validation.Valid;

import com.github.iahrari.orderexample.dto.OrderDTO;
import com.github.iahrari.orderexample.service.OrderService;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    
    @PostMapping
    public OrderDTO addOrder(@Valid @RequestBody OrderDTO orderDTO) 
            throws MethodArgumentNotValidException{
        return orderService.saveOrder(orderDTO);
    }

    @GetMapping
    public List<OrderDTO> getAll(){
        return orderService.getAllOrders();
    }
}
