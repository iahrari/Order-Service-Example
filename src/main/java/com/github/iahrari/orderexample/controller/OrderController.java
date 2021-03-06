package com.github.iahrari.orderexample.controller;

import java.util.List;

import javax.validation.Valid;

import com.github.iahrari.orderexample.dto.OrderDTO;
import com.github.iahrari.orderexample.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiImplicitParam(
            name = "Authorization",
            value = "Access Token",
            required = true,
            paramType = "header",
            example = "Bearer access_token")
    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.debug("HTTP request to save a new order : {}", orderDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.saveOrder(orderDTO));
    }

    @ApiImplicitParam(
            name = "Authorization",
            value = "Access Token",
            required = true,
            paramType = "header",
            example = "Bearer access_token")
    @GetMapping
    public List<OrderDTO> getAll() {
        return orderService.getAllOrders();
    }

    @ApiImplicitParam(
            name = "Authorization",
            value = "Access Token",
            required = true,
            paramType = "header",
            example = "Bearer access_token")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }
}
