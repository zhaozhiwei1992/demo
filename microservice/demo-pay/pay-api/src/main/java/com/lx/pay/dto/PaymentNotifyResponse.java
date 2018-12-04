package com.lx.pay.dto;

import com.lx.pay.commons.AbstractResponse;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PaymentNotifyResponse extends AbstractResponse implements Serializable {

    private static final long serialVersionUID = -4767584167298163287L;
    private String result;//返回给服务端的执行结果的报文

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PaymentNotifyResponse{" +
                "result='" + result + '\'' +
                "} " + super.toString();
    }
}
