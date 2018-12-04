package com.lx.pay.dto;


import com.lx.pay.commons.AbstractRequest;

import java.io.Serializable;
import java.util.Map;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PaymentNotifyRequest extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = -7411647421270474844L;

    /**
     * 支付渠道（alipay：支付宝  /  wechat_pay：微信）
     */
    private String payChannel;

    private Map<String,Object> resultMap; //服务端返回的支付通知结果

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void requestCheck() {

    }

    @Override
    public String toString() {
        return "PaymentNotifyRequest{" +
                "payChannel='" + payChannel + '\'' +
                ", resultMap=" + resultMap +
                "} " + super.toString();
    }
}
