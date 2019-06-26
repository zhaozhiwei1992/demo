package com.lx.demo.multicast;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.*;

/**
 * 启动多个线程接受组播发送消息
 */
public class MulticastClient {

    public static void main(String[] args) {

        /**
         * public ThreadPoolExecutor(int corePoolSize,
         *                               int maximumPoolSize,
         *                               long keepAliveTime,
         *                               TimeUnit unit,
         *                               BlockingQueue<Runnable> workQueue)
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                15,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue(1024),
                new ThreadFactoryBuilder().setNameFormat("guava-%d").build(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        //启动10个客户端来读取消息
        for (int i = 0; i < 20; i++) {
//            new Thread(()-> muticastClientSub()).start();
//            Executors.newSingleThreadExecutor().execute(MulticastClient ::muticastClientSub);
            executor.execute(MulticastClient::muticastClientSub);
        }
    }

    private static void muticastClientSub() {
        //加入到指定组
        try {
            InetAddress group = InetAddress.getByName("224.5.6.7");

            MulticastSocket socket = new MulticastSocket(8888);
            socket.joinGroup(group);

            //这个大小要根据传入的大小控制
            byte[] bytes = new byte[10];

            while (true){
                //数据包
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                socket.receive(datagramPacket);

                String s = new String(datagramPacket.getData());
                System.out.println(Thread.currentThread().getName() + " 获取到服务器消息: " + s);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
