package com.example.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(value = {InputOutputChannel.class})
public class DicStreamListener {

    /**
     * 监听消息
     * condition 通过条件获取消息
     * @param message
     */
    @StreamListener(value = InputOutputChannel.MESSAGEOUTPUT, condition = "headers['TAGS'] == 'basicdata_version'")
    public void receiveDicMessage(@Payload String message) {
        log.info("获取用户增加成功消息: {}", message);

        // 消息转换, json解析
        //发送邮件
    }

}
