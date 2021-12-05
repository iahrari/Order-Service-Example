package com.github.iahrari.orderexample.service;

import java.util.List;

import com.github.iahrari.orderexample.dto.OrderDTO;
import com.github.iahrari.orderexample.dto.PriceResponse;
import com.github.iahrari.orderexample.dto.converter.DtoToOrder;
import com.github.iahrari.orderexample.dto.converter.OrderToDTO;
import com.github.iahrari.orderexample.repository.OrderRepository;

import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final DtoToOrder dtoToOrder;
    private final OrderToDTO orderToDTO;
    
    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO) 
            throws MethodArgumentNotValidException {
        var entity = restTemplate.getForEntity(
            "http://localhost:8081/price?source={source}&destination={destination}", 
            PriceResponse.class, orderDTO.getSource(), orderDTO.getDestination());
        System.out.println(entity.getBody());
        if(entity.getStatusCode().is2xxSuccessful()){
            var order = dtoToOrder.convert(orderDTO);
            order.setPrice(entity.getBody().getPrice());
            return orderToDTO.convert(orderRepository.save(order));
        } else {
            var bindingResult = new BeanPropertyBindingResult(orderDTO, "order");
            var valueIterator = entity.getBody().getValues().iterator();
            entity.getBody().getFields().forEach(field -> {
                bindingResult.rejectValue(field, valueIterator.next());
            });
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
    }

    @Override
    public List<OrderDTO> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(orderToDTO::convert)
                .toList();
    }
    
}
