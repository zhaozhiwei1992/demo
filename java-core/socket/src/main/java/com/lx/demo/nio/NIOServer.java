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

    /**
     * @Description: 服务端消息处理器
     * @author zhaozhiwei
     * @date 2021/10/24 下午9:34
     * @version V1.0
     */
    private static class Handler {

        private int bufferSize = 1024;

        private String localCharset = "UTF-8";



        public Handler() {

        }

        public Handler(int bufferSize) {
            this(bufferSize, null);
        }

        public Handler(String localChaset){
            this(-1, localChaset);
        }

        private Handler(int bufferSize, String localCharset) {
            if(bufferSize > 0){
                this.bufferSize = bufferSize;
            }
            if(!Objects.isNull(localCharset)){
                this.localCharset =localCharset;
            }
        }

        public void handleAccept(SelectionKey selectionKey) throws IOException {
//        使用SelectionKey获取到具体的Channel, Selector操作类型操作
            final SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        /**
         * @data: 2021/10/24-下午9:56
         * @User: zhaozhiwei
         * @method: handleRead
          * @param selectionKey :
         * @return: void
         * @Description: 描述
         *  Creates a new buffer with the given
         *  mark, 暂存position的值，position暂存到mark中就可以进行相关修改操作, 操作完成后可以reset恢复到position
         *  position, 当前操作元素索引位置, 起始位置为0, put, get时候自动更新
         *  limit, 可以使用的上限, 初始化与capacity相同, 但是<=capacity, 假如初始化为100, 但是在buffer中之写入了20的数据, 则读取是设置为20
         *  and capacity 容量: 最多可以保存元素数量, 使用过程中不可变
         *
         *  mark <= position <= limit <= capacity
         */
        public void handleRead(SelectionKey selectionKey) throws IOException {
//            获取channel
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
                final String receivedString = Charset.forName(localCharset).newDecoder().decode(byteBuffer).toString();
                System.out.println("received from client: " + receivedString);

                String sendStr = "received data: " + receivedString;
                byteBuffer = ByteBuffer.wrap(sendStr.getBytes(localCharset));
                socketChannel.write(byteBuffer);
                socketChannel.close();
            }
        }
    }
}
