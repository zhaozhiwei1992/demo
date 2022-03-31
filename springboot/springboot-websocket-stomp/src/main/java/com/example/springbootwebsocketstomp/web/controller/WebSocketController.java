package com.example.springbootwebsocketstomp.web.controller;

import com.example.springbootwebsocketstomp.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: WebSocketController
 * @Package com/example/springbootwebsocketstomp/web/controller/WebSocketController.java
 * @Description:
 *
 * 步骤2: 创建消息接收的链接, 主要是MessageMapping注解
 *
 * @author zhaozhiwei
 * @date 2022/3/31 下午6:30
 * @version V1.0
 */
@Slf4j
@RestController
public class WebSocketController {

    /**
     * @data: 2022/3/31-下午5:55
     * @User: zhaozhiwei
     * @method: sendMsg
      * @param value :
     * @return: java.lang.String
     * @Description: 描述
     * 注解与Spring MVC RequestMapping类似，它是定义 WebSocket请求的路径，
     * 当然需要与 WebSocketConfig 所定义的前缀”request”连用。
     * sendMsg方法上还添加了@SendTo注解，说明在执行完成这个方法后，会将返回结果发送到订阅的这个目的地中，这样客户端就可以得到消息
     *
     * @MessageMapping注解方法的返回值所形成的消息，将会路由到代理上
     */
    @MessageMapping("/send")
    @SendTo("/sub/chat")
    public String sendMsg(String value) {
        log.info("收到前端传入消息 {}", value);
        return "服务端返回, " + value;
    }

    @Autowired
    private WebSocketService webSocketService;

    /**
     * @data: 2022/3/31-下午5:58
     * @User: zhaozhiwei
     * @method: sendMsg2
      * @param value :
     * @return: void
     * @Description:
     * SimpMessagingTemplate是Spring-WebSocket内置的一个消息发送工具，可以将消息发送到指定的客户端。
     *
     * {@see @SendTo}
     * 手动发送消息到另外的客户端
     */
    @MessageMapping("/send2")
    public void sendMsg2(String value) {
        System.out.println(value);
        webSocketService.sendMsg(value);
    }
}