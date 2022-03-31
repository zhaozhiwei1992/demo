package com.example.web.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.handler
 * @Description: 你说hello 我说world
 * @date 2022/3/31 上午9:32
 */
public class HelloWorldHandler extends AbstractWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        logger.info("receive message: {}", message.getPayload());

        Thread.sleep(2000);

        session.sendMessage(new TextMessage("World"));
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("connection closed, status: {}", status);
    }
}
