package com.lx.demo.nio.http;

import com.lx.demo.nio.Handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.nio
 * @Description: TODO
 * @date 2021/10/24 下午11:29
 */
public class HttpHandler extends Handler implements Runnable {

    private SelectionKey selectionKey;

    public HttpHandler(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    @Override
    public void run() {
        try {
            if(selectionKey.isAcceptable()){
                this.handleAccept(selectionKey);
            }
//                读取数据
            if(selectionKey.isReadable()){
                this.handleRead(selectionKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 解析http报文
     * 显示报文: 接收到的请求报文是:
     * GET / HTTP/1.1
     * Host: 127.0.0.1:8888
     * Connection: keep-alive
     * Cache-Control: max-age=0
     * sec-ch-ua: "Google Chrome";v="93", " Not;A Brand";v="99", "Chromium";v="93"
     * sec-ch-ua-mobile: ?0
     * sec-ch-ua-platform: "Linux"
     * Upgrade-Insecure-Requests: 1
     * User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63
     * Safari/537.36
     * Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*//*;q=0.8,application/signed-exchange;v=b3;q=0.9
//            *Sec-Fetch-Site:none
//     *Sec-Fetch-Mode:navigate
//     *Sec-Fetch-User:?1
//            *Sec-Fetch-Dest:document
//     *Accept-Encoding:gzip,deflate,br
//     *Accept-Language:zh-CN,zh;q=0.9
//     *
     */
    @Override
    public void handleRead(SelectionKey selectionKey) throws IOException {
        final SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
//            获取buffer并　clear
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        byteBuffer.clear();

//            读取内容
//            没有读到内容就关闭
        if(socketChannel.read(byteBuffer) == -1){
            socketChannel.close();
        }else{
//                buffer转换为读取状态
            byteBuffer.flip();

//                buffer读取到的值按照localCharset设置的格式转码
            final String receivedString = Charset.forName(getLocalCharset()).newDecoder().decode(byteBuffer).toString();
            if(receivedString.isEmpty()){
                socketChannel.close();
                return;
            }
            System.out.println("received from client: " + receivedString);

            // 解析http报文
//            报文结构固定, 首行, 头, 体, 换行分隔
//            报文头
            final String[] requestMsg = receivedString.split("\r\n");
            System.out.println("报文头: ");
            for (String s : requestMsg) {
                if(s.isEmpty()) break;
                System.out.println(s);
            }

            System.out.println("首行: ");
            final String[] firstLine = requestMsg[0].split(" ");
            System.out.println();
            System.out.println("Method: " + firstLine[0]);
            System.out.println("URL " + firstLine[1]);
            System.out.println("HTTP Version " + firstLine[2]);

            final StringBuffer responseMsg = new StringBuffer();
            responseMsg.append("HTTP/1.1 200 OK \r\n");
            responseMsg.append("Content-Type: text/html;charset=" + getLocalCharset() + "\r\n");
            responseMsg.append("\r\n");

            responseMsg.append("显示报文: \r\n");
            responseMsg.append("接收到的请求报文是: \r\n");

            responseMsg.append("</br>");
            for (String s : requestMsg) {
                responseMsg.append(s + "</br>");
            }
            byteBuffer = ByteBuffer.wrap(responseMsg.toString().getBytes(getLocalCharset()));
            socketChannel.write(byteBuffer);
            socketChannel.close();
        }

    }
}
