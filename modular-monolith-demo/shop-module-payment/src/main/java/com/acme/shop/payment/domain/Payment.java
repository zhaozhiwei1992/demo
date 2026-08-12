package com.acme.shop.payment.domain;

/**
 * 支付凭证（领域模型，值对象风格）。纯 POJO，不依赖 Spring / JPA。
 * 落地取舍同 order 模块：严格 DDD 需另建 PaymentEntity + 映射；
 * 务实派直接给本 record 加 JPA 注解（@Entity + @Id），见笔记 2.5。
 */
public record Payment(Long paymentId, Long orderId, int amount) {
}
