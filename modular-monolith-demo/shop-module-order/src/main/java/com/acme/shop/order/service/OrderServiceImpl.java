package com.acme.shop.order.service;

import com.acme.shop.order.api.OrderApi;
import com.acme.shop.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderApi {

    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long createOrder(String goodsCode, int count) {
        return repository.save(goodsCode, count);
    }
}
