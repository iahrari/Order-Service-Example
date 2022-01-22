package com.github.iahrari.orderexample.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.github.iahrari.orderexample.config.PricingServiceHandler;
import com.github.iahrari.orderexample.domain.Order;
import com.github.iahrari.orderexample.dto.OrderDTO;
import com.github.iahrari.orderexample.exception.OrderException;
import com.github.iahrari.orderexample.mapper.OrderMapper;
import com.github.iahrari.orderexample.repository.OrderRepository;
import com.github.iahrari.orderexample.service.OrderService;
import com.github.iahrari.orderexample.utils.IdGenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PricingServiceHandler pricingServiceConfig;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Value("${pricing-service.url}")
    private String pricingServiceUrl;

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) {

        log.debug("Request to insert a new order {}", orderDTO);
        Order orderEntity = orderMapper.toEntity(orderDTO);
        orderEntity.setHashId(IdGenerator.generateId());
        var price = getPrice(orderDTO.getSource(), orderDTO.getDestination());
        orderEntity.setPrice(BigDecimal.valueOf(price));
        Order savedOrder = orderRepository.save(orderEntity);
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new OrderException("Order not found"));
    }

    private Double getPrice(String source, String destination) {
        return pricingServiceConfig.getPrice(source, destination);
    }

}
