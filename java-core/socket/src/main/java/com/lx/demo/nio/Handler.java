package com.lx.demo.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @Description: 服务端消息处理器
 * @author zhaozhiwei
 * @date 2021/10/24 下午9:34
 * @version V1.0
 */
public class Handler {

    private int bufferSize = 1024;

    private String localCharset = "UTF-8";

    public int getBufferSize() {
        return bufferSize;
    }

    public String getLocalCharset() {
        return localCharset;
    }

    public Handler() {

    }

    public Handler(int bufferSize) {
        this(bufferSize, null);
    }

    public Handler(String localChaset){
        this(-1, localChaset);
    }

    public Handler(int bufferSize, String localCharset) {
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
