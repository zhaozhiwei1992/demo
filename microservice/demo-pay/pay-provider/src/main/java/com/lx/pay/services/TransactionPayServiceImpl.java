package com.lx.pay.services;

import com.lx.pay.TransactionPayService;
import com.lx.pay.biz.abs.BasePayment;
import com.lx.pay.dto.PaymentNotifyRequest;
import com.lx.pay.dto.PaymentNotifyResponse;
import com.lx.pay.dto.PaymentRequest;
import com.lx.pay.dto.PaymentResponse;
import com.lx.pay.exception.ExceptionUtil;
import com.lx.pay.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("transactionPayService")
public class TransactionPayServiceImpl implements TransactionPayService {

    private final Logger Log = LoggerFactory.getLogger(TransactionPayServiceImpl.class);


    @Override
    public PaymentResponse execPay(PaymentRequest request) {
        PaymentResponse paymentResponse=new PaymentResponse();
        try {
            paymentResponse=(PaymentResponse) BasePayment.paymentMap.
                    get(request.getPayChannel()).process(request);
            //TODO 发送消息通知，可以通知清结算这边记录交易、或者通知营销平台发放优惠券

        }catch (Exception e){
            Log.error("execPay occur exception:"+e);
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            paymentResponse.setCode(serviceException.getErrorCode());
            paymentResponse.setMsg(serviceException.getErrorMessage());
        }finally {
            Log.info("execPay return result:"+paymentResponse);
        }
        return paymentResponse;
    }

    /**
     * 支付结果回调
     * @param request
     * @return
     */
    @Override
    public PaymentNotifyResponse paymentResultNotify(PaymentNotifyRequest request) {
        Log.info("paymentResultNotify request:"+request);
        PaymentNotifyResponse response=new PaymentNotifyResponse();
        try{
            response=(PaymentNotifyResponse) BasePayment.paymentMap.
                    get(request.getPayChannel()).completePayment(request);
            //发送消息
            
        }catch (Exception e){
            Log.error("paymentResultNotify occur exception:"+e);
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            Log.info("paymentResultNotify return result:"+response);
        }
        return response;
    }
}
