package com.acme.shop.payment.web;

import com.acme.shop.payment.api.PaymentApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** web 层只依赖 api 契约（实现类是 application 层的 PaymentApplicationService）。 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentApi paymentApi;

    public PaymentController(PaymentApi paymentApi) {
        this.paymentApi = paymentApi;
    }

    @PostMapping("/charge")
    public boolean charge(@RequestParam Long orderId, @RequestParam int amount) {
        return paymentApi.charge(orderId, amount);
    }
}
