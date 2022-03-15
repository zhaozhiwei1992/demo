//package com.lx.demo;
//
///**
// * @author zhaozhiwei
// * @version V1.0
// * @Title: null.java
// * @Package com.lx.demo
// * @Description: TODO
// * @date 2022/3/15 下午5:59
// */
//public class IpUtil {
//
//    public static String getIp(HttpServletRequest request) {
//        String ip = request.getHeader("X-Cluster-Client-Ip");
//        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
//            logger.debug("header is X-Forwarded-For");
//            ip = request.getHeader("X-Forwarded-For");
//        }
//        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
//            logger.debug("header is Proxy-Client-IP");
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
//            logger.debug("header is WL-Proxy-Client-IP");
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
//            logger.debug("header is HTTP_CLIENT_IP");
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
//            logger.debug("header is HTTP_X_FORWARDED_FOR");
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (StringUtils.isBlank(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        if (StringUtils.isBlank(ip)) {
//            return UNKNOWN_IP;
//        }
//        return ip;
//    }
//
//}
