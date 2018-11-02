package com.lx.demo.server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 */
public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(8888);
            System.out.println("启动服务器....");
            Socket accept = socket.accept();
            System.out.println("客户端:"+ InetAddress.getLocalHost()+"已连接到服务器");
            InputStream inputStream = accept.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String s;
            while ((s=bufferedReader.readLine()) != null){
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
