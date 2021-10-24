package com.lx.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.nio
 * @Description:
 * 创建非阻塞式Server
 * 测试:
 * ** telnet
 * 1. telnet 127.0.0.1 8888
 * 2. 随便输入内容, 会收到 received data:  xx
 * ** curl
 * curl -vv telnet://127.0.0.1:8888
 * @date 2021/10/24 下午9:03
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {
//        1. 创建ServerSocketChannel并设置相应参数
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        监听8888端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
//        设置非阻塞
        serverSocketChannel.configureBlocking(false);
//        2. 创建Selector并注册到ServerSocketChannel上
        final Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server started on port: " + 8888);

//        ByteBuffer buffer = ByteBuffer.allocate(64);
//        创建处理器
        final Handler handler = new Handler(1024);

//        3. 调用Selector的select()方法等待请求
        while(true){
            if(selector.select(3000)==0){
                System.out.println("等待请求超时");
                continue;
            }
            System.out.printf("继续运行....");
//        4. Selecttor接收到请求后, 立即返回selectedkeys集合
            final Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()){
                final SelectionKey selectionKey = selectionKeyIterator.next();
//        5. 使用SelectionKey获取到具体的Channel, Selector操作类型操作
//                接收到连接请求时
                if(selectionKey.isAcceptable()){
                    handler.handleAccept(selectionKey);
                }
//                读取数据
                if(selectionKey.isReadable()){
                    handler.handleRead(selectionKey);
                }
//                处理完成本次操作, 移除
                selectionKeyIterator.remove();
            }
        }
    }

}
