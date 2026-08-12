package com.acme.shop.order.domain;

/**
 * 仓库接口放 domain（依赖倒置），实现放 infrastructure。
 * 领域层不关心底层是 JPA / MyBatis / 内存——这正是可测试性的来源。
 *
 * 关键点：这个接口【不需要手写实现类】。真实项目里建一个 Spring Data 接口继承它：
 *   interface OrderJpaRepository extends JpaRepository<OrderEntity, Long>, OrderRepository
 * Spring 启动时自动代理生成实现，save/findById 全自动。
 * demo 里 OrderInfraRepository 这个手写实现只是内存替身（顺便当纯 JUnit 测试的替身）。
 */
public interface OrderRepository {

    Order save(Order order);

    Order findById(Long orderId);
}
