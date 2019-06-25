package com.lx.demo.simplex;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {

        //建立连接
        Socket socket = new Socket("127.0.0.1", 8888);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        bufferedWriter.write("hello world");

        System.out.println("写出完成..");
        bufferedWriter.close();
        socket.close();
    }
}
