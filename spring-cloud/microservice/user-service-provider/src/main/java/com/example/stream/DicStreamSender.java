package com.example.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableBinding(value = {InputOutputChannel.class})
public class DicStreamSender {
    private Logger logger = LoggerFactory.getLogger(DicStreamSender.class);

    @Autowired
    private InputOutputChannel InputOutputChannel;

    public void sendMessage(String msg){
        String tag = "basicdata_version";//tag为二级分类，topic为一级分类，可以根据两个类别进行订阅
        Message message = MessageBuilder.createMessage(msg,
                new MessageHeaders(Stream.of(tag).collect(Collectors.toMap(str -> "TAGS", String::toString))));
        logger.info("send message-> {}", message);

        InputOutputChannel.output().send(message);
    }
}
