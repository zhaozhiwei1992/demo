package com.acme.shop.order.web;

import com.acme.shop.order.api.OrderApi;
import com.acme.shop.order.api.OrderCreateCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * web 层只依赖 api（契约）：注入接口 OrderApi，实现类是 application 层的
 * OrderApplicationService。不碰 domain / infrastructure。
 * 将来 OrderApi 实现换成远程 FeignClient，本类一行都不用改。
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
        return orderApi.createOrder(new OrderCreateCommand(goodsCode, count));
    }
}
