package com.lx.demo.springbootkafka.serialization;


import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * 对象序列化
 */
public class ObjectSerializer implements Serializer<Object> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    /**
     * 强行理解, 对于序列化器来说，是从内存中读取数据并序列化输出, 所以这里使用outputstream
     * @param topic
     * @param object
     * @return
     */
    @Override
    public byte[] serialize(String topic, Object object) {
        System.out.println("topic : " + topic + " , object : " + object);

        byte[] dataArray;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            dataArray = outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dataArray;
    }

    @Override
    public void close() {

    }
}
