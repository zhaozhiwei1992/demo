package com.acme.shop.order.application;

import com.acme.shop.order.api.OrderCreateCommand;
import com.acme.shop.order.domain.Order;
import com.acme.shop.order.domain.OrderStatus;
import com.acme.shop.order.infrastructure.OrderInfraRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * DDD 可测试性示范：application/domain 无 Spring 依赖，
 * 用 infrastructure 的内存实现直接 new 出来测——毫秒级、不起 Spring 上下文。
 */
class OrderApplicationServiceTest {

    @Test
    void createOrder_persistsAggregate() {
        OrderInfraRepository repo = new OrderInfraRepository();
        OrderApplicationService service = new OrderApplicationService(repo);

        Long id = service.createOrder(new OrderCreateCommand("A01", 2));

        Order saved = repo.findById(id);
        assertThat(saved.getGoodsCode()).isEqualTo("A01");
        assertThat(saved.getCount()).isEqualTo(2);
        assertThat(saved.getStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    void aggregate_guards_state_transition() {
        Order order = new Order(1L, "A01", 1);

        order.markPaid();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);

        // 已支付再支付 → 领域规则拒绝
        assertThatThrownBy(order::markPaid)
            .isInstanceOf(IllegalStateException.class);
    }
}
