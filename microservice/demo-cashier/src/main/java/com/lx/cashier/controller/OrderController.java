package com.lx.cashier.controller;

import com.lx.common.annotation.Anoymous;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 * 模拟订单调用
 */
@Anoymous
@RestController
public class OrderController extends BaseController{

    Logger LOG=LoggerFactory.getLogger(OrderController.class);

    /*@Autowired
    PaymentService paymentService;
    *//**
     * 下单操作，下单后，需要冻结账户资金，支付成功以后进行实际扣除。否则解冻
     *
     * @return
     *//*
    @RequestMapping(value = "/placeorder", method = RequestMethod.GET)
    public String placeOrder(){
        //构建一个订单实例，支付人和收款人
        Order order=new Order(1000,2000);
        BigDecimal orderAmount=new BigDecimal(4000);//订单需要支付的金额
        try {
            paymentService.makePayment(order, orderAmount);
        }catch (Exception e) {
            LOG.error("placeOrder occur Exception:"+e);
        }
        return order.getMerchantOrderNo();
    }*/

}
