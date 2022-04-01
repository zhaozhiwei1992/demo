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

    /**
     * @Description: 使用SimpMessagingTemplate,可以任意地方发消息，不用依赖先收到消息(@SendTo)
     */
    @Autowired
    private SimpMessagingTemplate template;
    
    public void sendMsg(String message) {
    	if (message != null && message.trim().length() > 0) {
//           监听 /sub/chat的都能收到, 所以client02发消息, client01也能收到, 前提是打开 内存的STOMP消息代理, sub开头入不了rabbitmq
    		template.convertAndSend("/sub/chat", "服务端send2: " + message);
//           监听 /queue/chat的都能收到, 所以client01发的消息 只SendTo /sub, 只有client01收的到
            template.convertAndSend("/queue/chat", "服务端send2: " + message);
    	}
    }
}