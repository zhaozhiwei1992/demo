package com.acme.shop.payment.application;

import com.acme.shop.payment.api.PaymentApi;
import com.acme.shop.payment.domain.Payment;
import com.acme.shop.payment.domain.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 应用服务（用例层）：实现 PaymentApi 契约，编排领域对象，管事务。 */
@Service
public class PaymentApplicationService implements PaymentApi {

    private final PaymentRepository repository;

    public PaymentApplicationService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public boolean charge(Long orderId, int amount) {
        return repository.save(new Payment(System.nanoTime(), orderId, amount)) != null;
    }
}
