package com.lx.demo.springboot.markdown.util;

import java.net.URI;
import java.net.URISyntaxException;

public class URLIPReplacer {
    
    /**
     * 替换URL中的IP地址和端口
     * @param originalUrl 原始URL
     * @param newBaseUrl 新的基础URL（如 "http://192.168.1.100:8080"）
     * @return 替换后的URL
     */
    public static String replaceIPAndPort(String originalUrl, String newBaseUrl) {
        try {
            // 解析原始URL
            URI originalUri = new URI(originalUrl);
            
            // 解析新的基础URL
            URI newBaseUri = new URI(newBaseUrl);
            
            // 构建新的URI，保留原始URL的路径和查询参数
            URI newUri = new URI(
                newBaseUri.getScheme() != null ? newBaseUri.getScheme() : originalUri.getScheme(),
                newBaseUri.getAuthority(), // 这会使用新URI的authority（用户信息、主机和端口）
                originalUri.getPath(),
                originalUri.getQuery(),
                originalUri.getFragment()
            );
            
            return newUri.toString();
            
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URL格式错误", e);
        }
    }
    
    /**
     * 只替换IP地址，保持原始端口
     * @param originalUrl 原始URL
     * @param newIp 新的IP地址（如 "192.168.1.100"）
     * @return 替换后的URL
     */
    public static String replaceIPOnly(String originalUrl, String newIp) {
        try {
            URI originalUri = new URI(originalUrl);
            
            // 构建新的URI，只替换IP地址，保持其他部分不变
            URI newUri = new URI(
                originalUri.getScheme(),
                originalUri.getUserInfo(),
                newIp,
                originalUri.getPort(),
                originalUri.getPath(),
                originalUri.getQuery(),
                originalUri.getFragment()
            );
            
            return newUri.toString();
            
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URL格式错误", e);
        }
    }
    
    // 使用示例
    public static void main(String[] args) {
        String originalUrl = "http://59.203.26.51:8001/esms/image?fileName=jk_bar.png&tokenid=b3ca53be53c141d19fde2d1b996ead86dDlBdpfT";
        
        // 示例1：替换整个基础URL
        String newBaseUrl = "http://192.168.30.156:8001";
        String result1 = replaceIPAndPort(originalUrl, newBaseUrl);
        System.out.println("替换基础URL: 原: " + originalUrl + "\n 新:" + result1);
        
        // 示例2：只替换IP地址，保持端口8001
        String newIp = "192.168.1.100";
        String result2 = replaceIPOnly(originalUrl, newIp);
        System.out.println("只替换IP: " + result2);
    }
}