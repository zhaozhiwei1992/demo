package com.example.springbootwebsocketstomp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @Title: WebSocketService
 * @Package com/example/springbootwebsocketstomp/service/WebSocketService.java
 * @Description:
 * 实现了 @SendTo类似的功能, 没有复杂逻辑直接使用SendTo即可
 * @author zhaozhiwei
 * @date 2022/3/31 下午6:29
 * @version V1.0
 */
@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate template;
    
    public void sendMsg(String message) {
    	if (message != null && message.trim().length() > 0) {
    		template.convertAndSend("/sub/chat", "服务端send2: " + message);
    	}
    }
}