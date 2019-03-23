package com.lx.demo.springbootkafka.serialization;

import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * 对象反序列化
 */
public class ObjectDeSerializer implements Deserializer<Object> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    /**
     * 对于反序列化器来说， 是把序列化的结果从外部读取到内存， 所以用输出流, 或者可以理解object才是亲儿子， object在的一方是被输入的一方
     * @param topic
     * @param data
     * @return
     */
    @Override
    public Object deserialize(String topic, byte[] data) {
        Object object = null;

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

        try {
            ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);

            object = inputStream.readObject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(" topic : " + topic + " , deserialized object :" + object);

        return object;
    }

    @Override
    public void close() {

    }
}
