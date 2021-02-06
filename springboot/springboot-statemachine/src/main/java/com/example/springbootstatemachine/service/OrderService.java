package com.example.springbootstatemachine.service;

import com.example.springbootstatemachine.dto.Order;

import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: OrderService.java.java
 * @Package com.example.springbootstatemachine.service
 * @Description: TODO
 * @date 2021/2/5 下午2:54
 */
public interface OrderService {
    Order create();

    Order pay(int id);

    Order deliver(int id);

    Order receive(int id);

    Map<Integer, Order> getOrders();
}
