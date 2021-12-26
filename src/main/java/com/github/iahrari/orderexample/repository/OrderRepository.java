package com.github.iahrari.orderexample.repository;

import com.github.iahrari.orderexample.domain.Order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {
    
}
