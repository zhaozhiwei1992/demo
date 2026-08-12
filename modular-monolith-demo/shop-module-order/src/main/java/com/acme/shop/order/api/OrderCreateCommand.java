package com.acme.shop.order.api;

/** 创建订单的入参 DTO（契约的一部分）。 */
public record OrderCreateCommand(String goodsCode, int count) {
}
