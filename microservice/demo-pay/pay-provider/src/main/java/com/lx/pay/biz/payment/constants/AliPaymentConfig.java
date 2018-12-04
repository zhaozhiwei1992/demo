package com.lx.pay.biz.payment.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service
public class AliPaymentConfig {
    @Value("${ALI_SERVICE}")
    private String ali_service;

    @Value("${ALI_PARTNER}")
    private String ali_partner;

    @Value("${INPUT_CHARSET}")
    private String input_charset;

    @Value("${SIGN_TYPE}")
    private String sign_type;

    @Value("${NOTIFY_URL}")
    private String notify_url;

    @Value("${RETURN_URL}")
    private String return_url;

    @Value("${SELLER_ID}")
    private String seller_id;

    @Value("${SELLER_EMAIL}")
    private String seller_email;

    @Value("${PAY_GATEWAY_NEW}")
    private String pay_gateway_new;

    @Value("${IT_B_PAY}")
    private String it_b_pay;

    @Value("${PRIVATE_KEY}")
    private String private_key;// 商户的私钥

    @Value("${PUBLIC_KEY}")
    private String public_key;// 支付宝的公钥

    @Value("${PAY_OPEN_GATEWAY}")
    private String pay_open_gateway;

    public String getAli_service() {
        return ali_service;
    }

    public String getAli_partner() {
        return ali_partner;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public String getSign_type() {
        return sign_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public String getPay_gateway_new() {
        return pay_gateway_new;
    }

    public String getIt_b_pay() {
        return it_b_pay;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public String getPublic_key() {
        return public_key;
    }

    public String getPay_open_gateway() {
        return pay_open_gateway;
    }
}
