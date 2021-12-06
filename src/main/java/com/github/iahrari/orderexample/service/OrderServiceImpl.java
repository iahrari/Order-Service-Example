package com.github.iahrari.orderexample.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iahrari.orderexample.dto.OrderDTO;
import com.github.iahrari.orderexample.dto.PriceResponse;
import com.github.iahrari.orderexample.dto.converter.DtoToOrder;
import com.github.iahrari.orderexample.dto.converter.OrderToDTO;
import com.github.iahrari.orderexample.repository.OrderRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final DtoToOrder dtoToOrder;
    private final OrderToDTO orderToDTO;
    private final ObjectMapper mapper;
    
    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) 
            throws MethodArgumentNotValidException, JsonMappingException, JsonProcessingException {

        ResponseEntity<PriceResponse> entity = null;
        try {
            entity = restTemplate.getForEntity(
                "http://localhost:8081/price?source={source}&destination={destination}", 
                PriceResponse.class, orderDTO.getSource(), orderDTO.getDestination());

            var order = dtoToOrder.convert(orderDTO);
            order.setPrice(entity.getBody().getPrice());
            return orderToDTO.convert(orderRepository.save(order));
        } catch(HttpClientErrorException e){
            if(e.getStatusCode().value() == 400)
                throw new MethodArgumentNotValidException(null, rejectValue(e, orderDTO));

            throw e;
        }
    }

    private BindingResult rejectValue(HttpClientErrorException e, OrderDTO orderDTO) 
            throws JsonMappingException, JsonProcessingException {
        var body = mapper.readValue(e.getResponseBodyAsString(), PriceResponse.class);
        var bindingResult = new BeanPropertyBindingResult(orderDTO, "order");
        var valueIterator = body.getValue().iterator();
        body.getField().forEach(field -> {
            var value = valueIterator.next();
            bindingResult.rejectValue(field, value, value + " is not a valid value");
        });

        return bindingResult;
    }

    @Override
    public List<OrderDTO> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(orderToDTO::convert)
                .toList();
    }
    
}
