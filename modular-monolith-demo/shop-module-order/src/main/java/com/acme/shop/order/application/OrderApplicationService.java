package com.acme.shop.order.application;

import com.acme.shop.order.api.OrderApi;
import com.acme.shop.order.api.OrderCreateCommand;
import com.acme.shop.order.domain.Order;
import com.acme.shop.order.domain.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 应用服务（用例层）：编排领域对象、管事务，不含业务规则（规则在 domain 聚合里）。
 * 实现 OrderApi 契约。主键生成真实项目由 infrastructure（JPA）负责，demo 用时间戳。
 */
@Service
public class OrderApplicationService implements OrderApi {

    private final OrderRepository repository;

    public OrderApplicationService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Long createOrder(OrderCreateCommand command) {
        Order order = new Order(System.nanoTime(), command.goodsCode(), command.count());
        return repository.save(order).getOrderId();
    }
}
