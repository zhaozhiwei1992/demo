package com.acme.shop.payment.repository;

import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {

    public boolean markPaid(Long orderId) {
        return true; // 演示
    }
}
