package com.acme.shop.order.web;

import com.acme.shop.order.api.OrderApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * web 层只依赖契约 OrderApi（注入接口，不注入 OrderServiceImpl）。
 * 将来 OrderApi 的实现换成远程 FeignClient，本类一行都不用改。
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderApi orderApi;

    public OrderController(OrderApi orderApi) {
        this.orderApi = orderApi;
    }

    @PostMapping("/create")
    public Long create(@RequestParam String goodsCode, @RequestParam int count) {
        return orderApi.createOrder(goodsCode, count);
    }
}
