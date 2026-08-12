package com.acme.shop.order.infrastructure;

import com.acme.shop.order.domain.Order;
import com.acme.shop.order.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础设施层：OrderRepository 的实现。demo 用内存 Map 演示依赖方向
 * （infrastructure → domain，实现接口）。
 *
 * 真实项目这里【不一定需要这个类】：建一个接口即可，无需实现类——
 *   interface OrderJpaRepository extends JpaRepository<OrderEntity, Long>, OrderRepository
 * 两条路线任选（见笔记 2.5）：
 * 严格 DDD：OrderEntity(@Entity) + 本类改注入 JpaRepository + Mapper（entity↔domain 映射）；
 * 务实派：domain 的 Order 直接 @Entity，上面那个接口直接 extends JpaRepository<Order, Long>。
 *
 * 也正是因为接口在 domain、实现在这里，测试可以直接 new 本类当内存替身，不起 Spring。
 */
@Repository
public class OrderInfraRepository implements OrderRepository {

    private final Map<Long, Order> store = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        store.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Order findById(Long orderId) {
        return store.get(orderId);
    }
}
