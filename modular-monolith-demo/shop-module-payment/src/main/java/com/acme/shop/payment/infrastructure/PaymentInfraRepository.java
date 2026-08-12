package com.acme.shop.payment.infrastructure;

import com.acme.shop.payment.domain.Payment;
import com.acme.shop.payment.domain.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础设施层：内存实现。真实项目无需本类——建接口
 * PaymentJpaRepository extends JpaRepository<PaymentEntity, Long>, PaymentRepository 即可；
 * 或务实派直接让 domain 的 Payment 加 @Entity（取舍见笔记 2.5 与 order 模块注释）。
 */
@Repository
public class PaymentInfraRepository implements PaymentRepository {

    private final Map<Long, Payment> store = new ConcurrentHashMap<>();

    @Override
    public Payment save(Payment payment) {
        store.put(payment.paymentId(), payment);
        return payment;
    }
}
