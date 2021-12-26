package com.github.iahrari.orderexample.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.iahrari.orderexample.domain.Order;
import com.github.iahrari.orderexample.dto.OrderDTO;
import com.github.iahrari.orderexample.dto.PriceModel;
import com.github.iahrari.orderexample.exception.OrderException;
import com.github.iahrari.orderexample.exception.PriceResponseException;
import com.github.iahrari.orderexample.mapper.OrderMapper;
import com.github.iahrari.orderexample.repository.OrderRepository;
import com.github.iahrari.orderexample.utils.IdGenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final static String PRICE_ENDPOINT = "/api/v1/price";

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
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
        var price = PriceModel.builder()
                .source(source)
                .destination(destination)
                .build();
        try {
            var priceResponse = restTemplate.postForEntity(
                    pricingServiceUrl + PRICE_ENDPOINT, price,
                    PriceModel.class);

            return Optional.ofNullable(priceResponse.getBody())
                    .map(PriceModel::getPrice)
                    .orElseThrow(() -> new PriceResponseException("Body is null"));
        } catch (HttpClientErrorException e) {
            throw new PriceResponseException(String.format("Pricing service error code %d", e.getStatusCode().value()));
        }
    }

}
