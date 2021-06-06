package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example
 * @Description: 基于socker创建一个容器
 * 1. ip
 * 2. port
 * 扩展
 * 关闭监听
 * @date 2021/6/1 下午10:41
 */
public class TomcatServer {

    public static void main(String[] args) throws IOException {
        // 创建socket实例, 监听端口
        final ServerSocket serverSocket = new ServerSocket(8888);
        System.out.printf("tomcat服务启动, port %s \n", 8888);

        for(;;){

            // 监听连接，连接成功返回socket
            final Socket acceptSocket = serverSocket.accept();

            // 接收请求, 解析, Request
            final InputStream inputStream = acceptSocket.getInputStream();

            // 解析完成后反馈
            final OutputStream outputStream = acceptSocket.getOutputStream();

            outputStream.close();
            inputStream.close();
        }
        // 容器关闭操作(非必要)
    }
}
