package com.lx.demo.simplex;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * socket服务端
 * <p>
 * ipv4中 0.0.0.0 - 223.255.255.255 属于单播网络地址
 */
public class Server {

    public static void main(String[] args) {

        try {

            //创建服务
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("服务器启动，端口8888");

            while (true) {

                Socket accept = serverSocket.accept();

                //创建管道
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));

                //读取信息
                String s = bufferedReader.readLine();
                System.out.println("客户端消息: " + s);

                PrintWriter printWriter = new PrintWriter(accept.getOutputStream(), true);
                printWriter.println("hello mm");

//                printWriter.close();
//                bufferedReader.close();
                // 这个关闭 客户端报错 socket关闭
//            serverSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
