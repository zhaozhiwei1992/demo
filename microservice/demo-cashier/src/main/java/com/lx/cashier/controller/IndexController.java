package com.lx.cashier.controller;

import com.lx.pay.TransactionPayService;
import com.lx.pay.dto.PaymentRequest;
import com.lx.pay.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@RestController
public class IndexController extends BaseController{

    @Autowired
    TransactionPayService payService;

    @PostMapping("/execPay")
    public String execPay(HttpServletRequest request, HttpServletResponse response,String productName, int orderPrice, String payWayCode, String remark){
        PaymentRequest payServiceRequest=new PaymentRequest();
        payServiceRequest.setOrderFee(orderPrice);
        payServiceRequest.setPayChannel(payWayCode);
        payServiceRequest.setSpbillCreateIp(request.getRemoteAddr());
        payServiceRequest.setSubject(productName);
        payServiceRequest.setTotalFee(orderPrice);
        payServiceRequest.setTradeNo(UUID.randomUUID().toString().replace("-",""));//TODO，暂时随机生成一个
        payServiceRequest.setUserId(1);//(Integer.parseInt(getUid()));
        PaymentResponse payServiceResponse=payService.execPay(payServiceRequest);
        if(payServiceResponse.getCode().equals("000000")){
            try {
                response.setHeader("content-type", "text/html;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                out.write(payServiceResponse.getHtmlStr().getBytes("UTF-8"));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return payServiceResponse.getCode()+"->"+payServiceResponse.getMsg();
    }
}
