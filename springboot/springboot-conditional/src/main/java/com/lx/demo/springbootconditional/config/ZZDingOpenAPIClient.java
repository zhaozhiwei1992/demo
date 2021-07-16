package com.lx.demo.springbootconditional.config;


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
