package com.lx.demo.simplex;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 为甚么要用println {@see https://www.cnblogs.com/wxgblogs/p/5347996.html}
 * <p>
 * println == write + flush
 */
public class Client {

    public static void main(String[] args) {
//        sendOnce();

        sendMsg();
    }

    private static void sendMsg() {

        // 创建十个线程
        int threadCount = 10;
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < threadCount; i++) {
//            每秒发一条消息
            int finalI = i;
            fixedThreadPool.execute(() -> {
                try {

                    Thread.sleep(1000);

                    //建立连接, 每个连接只能一个通道, 如果多次必须开多个socket
                    Socket socket = new Socket("127.0.0.1", 8888);

                    PrintWriter bufferedWriter = new PrintWriter(socket.getOutputStream(), true);
                    bufferedWriter.write(Thread.currentThread() + ": msg: " + finalI + "\r\n");
                    bufferedWriter.flush();
                    System.out.println("写出完成..");

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String s = bufferedReader.readLine();
                    System.out.println("服务端传入消息" + s);


                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private static void sendOnce() {
        try {

            //建立连接
            Socket socket = new Socket("127.0.0.1", 8888);

            PrintWriter bufferedWriter = new PrintWriter(socket.getOutputStream(), true);
//            BufferedReader sysBuff = new BufferedReader(new InputStreamReader(System.in));
            // 这里不用println就死翘翘了
//            bufferedWriter.println(sysBuff.readLine());

//            bufferedWriter.println("laji ");
            // 如果用write 显示加入换行
            bufferedWriter.write("lll222l\r\n");
            bufferedWriter.flush();
            System.out.println("写出完成..");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = bufferedReader.readLine();
            System.out.println("服务端传入消息" + s);

//            bufferedReader.close();
//            bufferedWriter.close();
//            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
