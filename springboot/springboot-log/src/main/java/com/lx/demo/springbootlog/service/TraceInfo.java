package com.lx.demo.springbootlog.service;

import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootlog.service
 * @Description: 通过threadlocal各线程独立保留请求信息
 * @date 2021/7/25 下午4:29
 */
public class TraceInfo {

    /**
     * @data: 2021/7/25-下午4:20
     * @User: zhaozhiwei
     * @method:
     * @param null :
     * @return:
     * @Description: 记录线程信息
     */
    private static final ThreadLocal local = new ThreadLocal();

    public static Map<String, Object> getTraceInfo() {
        return (Map<String, Object>) local.get();
    }

    public static void setTraceInfo(Map<String, Object> traceInfo) {
        local.remove();
        local.set(traceInfo);
    }
}
