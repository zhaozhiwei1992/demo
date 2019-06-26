package com.lx.demo.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * 启动多个线程接受组播发送消息
 */
public class MulticastClient {

    public static void main(String[] args) {
        //启动10个客户端来读取消息
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                new MulticastClient().muticastClientSub();
            }).start();
        }
    }

    private void muticastClientSub() {
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
