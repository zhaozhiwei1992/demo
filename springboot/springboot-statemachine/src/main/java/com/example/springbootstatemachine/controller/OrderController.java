package com.example.springbootstatemachine.controller;

import com.example.springbootstatemachine.dto.Order;
import com.example.springbootstatemachine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootstatemachine.controller
 * @Description: TODO
 * @date 2021/2/5 下午8:24
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @data: 2021/2/6-下午2:10
     * @User: zhaozhiwei
     * @method: create

     * @return: com.example.springbootstatemachine.dto.Order
     * @Description: 描述
     *
     * curl -X POST -H "Content-Type=application/json;charset=utf-8" http://127.0.0.1:8080/order/create
     */
    @PostMapping("/create")
    public Order create() {
        return orderService.create();
    }

    /**
     * @data: 2021/2/6-下午2:10
     * @User: zhaozhiwei
     * @method: pay
      * @param id :
     * @return: com.example.springbootstatemachine.dto.Order
     * @Description: 描述
     * curl -X POST -H "Content-Type=application/json;charset=utf-8" http://127.0.0.1:8080/order/pay/1
     */
    @PostMapping("/pay/{id}")
    public Order pay(@PathVariable int id) {
        return orderService.pay(1);
    }

    @PostMapping("/deliver/{id}")
    public Order deliver(@PathVariable int id) {
        return orderService.deliver(id);
    }

    @PostMapping("/receive/{id}")
    public Order receive(@PathVariable int id) {
        return orderService.receive(id);
    }

    @GetMapping("/orders")
    public Map<Integer, Order> getOrders() {
        return orderService.getOrders();
    }
}
