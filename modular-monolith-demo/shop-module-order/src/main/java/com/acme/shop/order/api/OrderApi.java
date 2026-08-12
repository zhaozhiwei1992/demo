package com.acme.shop.order.api;

/**
 * 订单对外契约（COLA client 思想：契约只放接口 + DTO）。
 * 实现类在 application 层（OrderApplicationService）。
 * 将来抽 RPC 时，把实现换成远程 FeignClient，调用方（web / 其他模块）不用改。
 */
public interface OrderApi {

    Long createOrder(OrderCreateCommand command);
}
