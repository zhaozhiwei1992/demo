package com.acme.shop.payment.domain;

/**
 * 仓库接口放 domain（依赖倒置），实现放 infrastructure。
 * 不需要手写实现类：Spring Data 接口 extends JpaRepository<PaymentEntity, Long>, PaymentRepository
 * 即自动生成；demo 的 PaymentInfraRepository 只是内存替身。
 */
public interface PaymentRepository {

    Payment save(Payment payment);
}
