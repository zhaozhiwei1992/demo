package com.acme.shop.order.api;

/**
 * 订单对外契约。其他模块（若需）只允许依赖本接口，不允许依赖 service/repository 实现类。
 */
public interface OrderApi {

    Long createOrder(String goodsCode, int count);
}
