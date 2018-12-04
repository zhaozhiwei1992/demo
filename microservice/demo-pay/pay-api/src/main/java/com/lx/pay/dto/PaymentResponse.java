package com.lx.pay.dto;

import com.lx.pay.commons.AbstractResponse;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PaymentResponse extends AbstractResponse implements Serializable {
    private static final long serialVersionUID = 436341660723282981L;

    private String htmlStr; //构建html表单

    private String prepayId; //微信支付下单的返回id
    private String codeUrl; //微信支付下单构建的二维码地址

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "htmlStr='" + htmlStr + '\'' +
                "} " + super.toString();
    }
}
