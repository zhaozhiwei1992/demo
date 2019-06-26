package com.lx.demo.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 通过组播服务发送多条消息
 */
public class MulticastServer {

    public static void main(String[] args) {
        // multicast地址段 224.0.0.0 - 239.255.255.255

        try {
            // 获取一个组播地址, 在这个地址组上都可以收到消息
            InetAddress group = InetAddress.getByName("224.5.6.7");

            MulticastSocket socket = new MulticastSocket();

            //间隔2秒发送一条总共发送10条消息
            for (int i = 0; i < 10; i++) {
                String msg = "hello mm";
                byte[] bytes = msg.getBytes();
                socket.send(new DatagramPacket(bytes, bytes.length, group, 8888));
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
