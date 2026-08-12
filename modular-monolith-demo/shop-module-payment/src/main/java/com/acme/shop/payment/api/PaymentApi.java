package com.acme.shop.payment.api;

/** 支付对外契约。实现类在 application 层。 */
public interface PaymentApi {

    boolean charge(Long orderId, int amount);
}
