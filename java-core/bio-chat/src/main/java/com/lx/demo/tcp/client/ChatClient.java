package com.lx.demo.tcp.client;

import com.lx.demo.tcp.util.ThreadManager;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

public class ChatClient{
    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
//            start();
//            ThreadManager.getThreadPollProxy().execute(()->start());
//            new Thread(ChatClient::start).start();
            Executors.newSingleThreadExecutor().execute(ChatClient::start);
        }
    }

    public static void start(){
        Socket socket = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        try {
            socket = new Socket("127.0.0.1", 9999);
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter.println(Thread.currentThread().getName() + "说: hello, my name is: " + Thread.currentThread().getName());
//            String msg = "";
//            while ((msg = bufferedReader.readLine()) != null){
//                System.out.println(msg);
//            }
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
