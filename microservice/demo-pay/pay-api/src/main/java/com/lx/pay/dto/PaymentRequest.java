package com.lx.pay.dto;

import com.lx.pay.commons.AbstractRequest;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class PaymentRequest extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -332222867014834720L;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 交易订单号, 统一生成全局唯一的订单号
     */
    private String tradeNo;

    /**
     * 实际支付金额(单位为分)
     */
    private Integer totalFee;

    /**
     * 订单总金额
     */
    private Integer orderFee;

    /**
     * 商品描述(必填)
     * 微信支付提交格式要求；支付宝不需要严格控制格式
     * {浏览器打开的网站主页title名 -商品概述}
     */
    private String subject;

    /**
     * 终端IP(非必填)
     */
    private String spbillCreateIp;

    /**
     * 支付渠道（alipay：支付宝  /  wechat_pay：微信）
     */
    private String payChannel;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(Integer orderFee) {
        this.orderFee = orderFee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    @Override
    public void requestCheck() {

    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "userId=" + userId +
                ", tradeNo='" + tradeNo + '\'' +
                ", totalFee=" + totalFee +
                ", orderFee=" + orderFee +
                ", subject='" + subject + '\'' +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", payChannel='" + payChannel + '\'' +
                "} " + super.toString();
    }
}
