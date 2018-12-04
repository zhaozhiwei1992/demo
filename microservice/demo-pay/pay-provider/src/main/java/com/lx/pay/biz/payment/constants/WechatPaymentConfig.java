package com.lx.pay.biz.payment.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service
public class WechatPaymentConfig {

    @Value("${WECHAT_APPID}")
    private String wechatAppid;

    @Value("${WECHAT_MCH_ID}")
    private String wechatMch_id;

    @Value("${WECHAT_APPSECRET}")
    private String wechatAppsecret;

    @Value("${WECHAT_MCHSECRET}")
    private String wechatMchsecret;

    @Value("${WECHAT_NOTIFYURL}")
    private String wechatNotifyurl;

    @Value("${WECHAT_UNIFIED_ORDER}")
    private String wechatUnifiedOrder;

    @Value("${WECHAT_ORDER_QUERY}")
    private String checkOrderUrl;

    public String getWechatAppid() {
        return wechatAppid;
    }

    public String getWechatMch_id() {
        return wechatMch_id;
    }

    public String getWechatAppsecret() {
        return wechatAppsecret;
    }

    public String getWechatMchsecret() {
        return wechatMchsecret;
    }

    public String getWechatNotifyurl() {
        return wechatNotifyurl;
    }

    public String getWechatUnifiedOrder() {
        return wechatUnifiedOrder;
    }

    public String getCheckOrderUrl() {
        return checkOrderUrl;
    }
}
