package com.lx.demo.tcp.server;

import com.lx.demo.tcp.util.ThreadManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * 聊天服务端程序
 * 只能启动一个server
 */
public class ChatServer {

    private static int PORT = 9999;

    /**
     * 不为空才创建
     */
    private static ServerSocket serverSocket;

    public static ServerSocket getInstance() throws IOException {
        if (serverSocket == null) {
            serverSocket = new ServerSocket(PORT);
        }
        return serverSocket;
    }

    /**
     * 启动一个服务器
     */
    public static void multConnectStart() {
        try {
            serverSocket = getInstance();
            System.out.println("服务器已启动....");

            //创建线程池(多个管家)来应付多个客户端连接
            ThreadManager.ThreadPollProxy threadPollProxy = ThreadManager.getThreadPollProxy();
            while (true) {
                Socket socket = serverSocket.accept();
//                System.out.println("客户端:"+ InetAddress.getLocalHost()+"已连接到服务器");
                //提交一个任务
//                threadPollProxy.execute(new ServerHandler(socket));
//                new Thread(new ServerHandler(socket)).start();
                Executors.newSingleThreadExecutor().execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单词传输， 用完就关闭
     */
    public static void singleConnectStart(){
        ServerSocket serverSocket = null;
        BufferedReader bufferedReader = null;
        PrintWriter writer = null;
        try {
            serverSocket = new ServerSocket(9999);
            Socket accept = serverSocket.accept();
            bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
            String msg = "";
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("已接收到客户端连接");
                System.out.println(msg);
            }

            writer = new PrintWriter(accept.getOutputStream());
            writer.print("wo laizi fuwuduan");
            writer.flush();

            writer.close();
            bufferedReader.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        multConnectStart();
    }

    /**
     * 专门为了给服务器处理客户端的io操作， 一个服务器可以配置多个管家
     */
    public static class ServerHandler implements Runnable{

        private Socket socket;

        public ServerHandler(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            InputStream inputStream = null;
            PrintWriter printWriter = null;
            try {
    //            System.out.println("管家" + Thread.currentThread().getName() + "为您服务");
                inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                printWriter = new PrintWriter(socket.getOutputStream(), true);

                String clientInfo = "";
                //循环读取时如果客户端关闭会抛出异常
                while ((clientInfo = bufferedReader.readLine()) != null){
                    System.out.println("客户端发送消息: " + clientInfo);
                    printWriter.println(clientInfo);
                    socket.shutdownOutput();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
