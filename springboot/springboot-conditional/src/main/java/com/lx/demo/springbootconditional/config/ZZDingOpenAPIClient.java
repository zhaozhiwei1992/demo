package com.lx.demo.springbootconditional.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package gov.mof.fasp2.pay.config
 * @Description:
 *  construct --> init ->destroy
 * @date 2021/7/16 下午4:40
 */
public class ZZDingOpenAPIClient {

    private static final Logger logger = LoggerFactory.getLogger(ZZDingOpenAPIClient.class);

    private String accessKey = "theone01-xxxx";

    private String secretKey = "xxxx";

    public ZZDingOpenAPIClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        logger.info("zzding init {}", accessKey);
    }

    public ZZDingOpenAPIClient() {
        System.out.println("zzding init ...");
    }

    public void  init(){
        String protocol = "https";
        String domain = "openplatform-pro.ding.zj.gov.cn";
        System.out.println("初始化zzding ....");
    }

    public void destroy(){
        System.out.println("销毁 zzding ....");
    }

    public String  doPost(String api, Map<String,String> params) {
        return api;
    }

    public String doGet(String api, Map<String, String> params) {
        return api;
    }
}
