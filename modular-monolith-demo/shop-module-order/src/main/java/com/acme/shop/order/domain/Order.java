package com.acme.shop.order.domain;

/**
 * 订单聚合（领域模型）。纯 POJO，不依赖 Spring / JPA。
 * 业务不变量收敛在这里，而不是散落在 service 里：
 * 状态流转规则（markPaid 的守卫）就是领域逻辑。
 *
 * 真实项目落地（二选一，见笔记 2.5）：
 * 严格 DDD：本类保持纯净，另建 OrderEntity(@Entity) + Mapper 双向映射（代价在此）；
 * 务实派（推荐小项目）：本类直接加 @Entity/@Id 注解，删掉 infrastructure 里的实现类，
 *   让 OrderJpaRepository extends JpaRepository<Order, Long> 自动生成存储，零转换代码。
 * 本 demo 用严格模式（纯净类 + 内存实现）只为演示依赖方向。
 */
public class Order {

    private final Long orderId;
    private final String goodsCode;
    private final int count;
    private OrderStatus status;

    public Order(Long orderId, String goodsCode, int count) {
        this.orderId = orderId;
        this.goodsCode = goodsCode;
        this.count = count;
        this.status = OrderStatus.CREATED;
    }

    public Long getOrderId() { return orderId; }
    public String getGoodsCode() { return goodsCode; }
    public int getCount() { return count; }
    public OrderStatus getStatus() { return status; }

    /** 聚合内业务规则：只有 CREATED 才能标记为已支付 */
    public void markPaid() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("订单状态不允许支付: " + status);
        }
        this.status = OrderStatus.PAID;
    }
}
