package com.lx.demo.springbootfeign.aop;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 记录feign对外请求日志
 */
@Component
public class FeignClientIntercepter implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignClientIntercepter.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Content-Type", "application/json");
        logger.info("这是自定义请求拦截器");

        //将获取Token对应的值往下面传
//        requestTemplate.header("token", getHeaders(getHttpServletRequest()).get("token"));
        /*String body=getBody(getHttpServletRequest());
        if(body.length()!=0){
            requestTemplate.body(body);
        }*/
//        getBodys(getHttpServletRequest(),requestTemplate);
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Feign拦截器拦截请求获取Token对应的值
     *
     * @param request
     * @return
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
    /*
     * @Author zqc
     * @Description //TODO Feign拦截器拦截请求获取参数对应的值(适用于post请求)
     * @Date 14:08 2019/12/31
     * @Param [request]
     * @return java.lang.String
     **/
    private String getBody(HttpServletRequest request){
        Enumeration<String> bodyNames = request.getParameterNames();
        StringBuffer body =new StringBuffer();
        if (bodyNames != null) {
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String values = request.getParameter(name);
                body.append(name).append("=").append(values).append("&");
            }
        }
        if(body.length()!=0) {
            body.deleteCharAt(body.length()-1);
        }
        return body.toString();
    }

    private void getBodys(HttpServletRequest request,RequestTemplate requestTemplate){
        Enumeration<String> bodyNames = request.getParameterNames();
        StringBuffer body =new StringBuffer();
        if (bodyNames != null) {
            Map map=new HashMap();
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String values = request.getParameter(name);
                requestTemplate.query(name, values);
                map.put(name,values);
            }
            logger.info("传入参数："+map);
        }
    }
}
