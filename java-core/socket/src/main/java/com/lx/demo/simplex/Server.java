package com.lx.demo.simplex;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * socket服务端
 *
 * ipv4中 0.0.0.0 - 223.255.255.255 属于单播网络地址
 */
public class Server {

    public static void main(String[] args) throws IOException {

        //创建服务
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务器启动，端口8888");
        Socket accept = serverSocket.accept();

        //创建管道
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));

        //读取信息
        String s = bufferedReader.readLine();
        System.out.println(s);

        System.out.println("读取完成");
        bufferedReader.close();
    }
}
