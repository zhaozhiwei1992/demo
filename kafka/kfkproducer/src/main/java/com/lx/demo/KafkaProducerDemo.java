package com.lx.demo;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerDemo extends Thread{
    private final KafkaProducer<Integer,String> producer;

    private final String topic;
    private final boolean isAysnc;

    private KafkaProducerDemo(String topic, boolean isAysnc){
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //use?
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"KafkaProducerDemo");
        properties.put(ProducerConfig.ACKS_CONFIG,"-1");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        producer=new KafkaProducer<Integer, String>(properties);
        this.topic=topic;
        this.isAysnc=isAysnc;
    }

    @Override
    public void run() {
        int num = 0;
        while (num < 52) {
            String message = "message_" + num;
            System.out.println("begin send message:" + message);
            if (isAysnc) {//异步发送
//                producer.send(new ProducerRecord<Integer, String>(topic, message), new Callback() {
//                    @Override
//                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                        if (recordMetadata != null) {
//                            System.out.println("async-offset:" + recordMetadata.offset() +
//                                    "->partition" + recordMetadata.partition());
//                        }
//                    }
//                });
                producer.send(new ProducerRecord<>(topic, message), (recordMetadata, e) -> {
                    if (recordMetadata != null) {
                        System.out.println("topic --> " + topic + " | partition --> " + recordMetadata.partition() + " | offset --> " + recordMetadata.offset());
                    }
                });
            } else {//同步发送  future/callable
                try {
                    RecordMetadata recordMetadata = producer.
                            send(new ProducerRecord<Integer, String>(topic, message)).get();
                    System.out.println("sync-offset:" + recordMetadata.offset() +
                            "->partition" + recordMetadata.partition());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            }
            num++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new KafkaProducerDemo("tt", true).start();
    }
}
