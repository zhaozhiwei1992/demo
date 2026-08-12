package com.acme.shop.payment.service;

import com.acme.shop.payment.api.PaymentApi;
import com.acme.shop.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentApi {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean charge(Long orderId, int amount) {
        return repository.markPaid(orderId);
    }
}
