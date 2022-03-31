package com.lx.demo.springbootwebsocket.web.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title: HelloWorldWebsocketServer
 * @Package com/lx/demo/springbootwebsocket/web/socket/HelloWorldWebsocketServer.java
 * @Description:
 * 步骤3: 创建WebSocket服务端
 * @author zhaozhiwei
 * @date 2022/3/31 上午11:39
 * @version V1.0
 */
@ServerEndpoint("/hello")
@Component
@Slf4j
public class HelloWorldWebsocketServer {
    /**
     * 存放所有在线的客户端
     */
    private static final Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        log.info("有新的客户端连接了: {}", session.getId());
        //将新用户存入在线的组
        clients.put(session.getId(), session);
    }

    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:{}", session.getId());
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param message  消息对象
     */
    @OnMessage
    public void onMessage(String message) throws InterruptedException {
        log.info("服务端收到客户端发来的消息: {}", message);

        Thread.sleep(2000);

        this.sendAll(message);
    }

    /**
     * 群发消息
     * @param message 消息内容
     */
    private void sendAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText("World");
        }
    }
}