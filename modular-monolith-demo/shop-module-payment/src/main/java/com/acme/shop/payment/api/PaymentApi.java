package com.acme.shop.payment.api;

public interface PaymentApi {

    boolean charge(Long orderId, int amount);
}
