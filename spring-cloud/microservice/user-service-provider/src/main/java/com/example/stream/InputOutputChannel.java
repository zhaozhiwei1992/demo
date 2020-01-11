package com.example.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 定义消息的Input和Output配置信息
 *
 * channel接收和发送的exchange一定要一致，比如发送用的是output,那么接收的也一定是output,否则接不到数据
 */
public interface InputOutputChannel {

    String MESSAGEINPUT = "message_input";

    String MESSAGEOUTPUT = "message_output";

    /**
     * 消息消费
     * Input注解作用
     *
     *     为每个binding生成channel实例
     *     指定channel名称
     *     message_input，类型为SubscribableChannel的bean
     *     在spring容器中生成一个类，InputOutputChannel。
     * @return
     */
    @Input(InputOutputChannel.MESSAGEINPUT)
    SubscribableChannel input();

    /**
     * 消息生产
     * @return
     */
    @Output(InputOutputChannel.MESSAGEOUTPUT)
    MessageChannel output();
}
