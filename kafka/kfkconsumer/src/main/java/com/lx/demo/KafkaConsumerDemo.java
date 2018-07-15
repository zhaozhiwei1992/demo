package com.lx.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerDemo extends Thread {
    private final KafkaConsumer<Integer, String> kafkaConsumer;

    /**
     * groupid
     */
    private String groupID;

    /**
     * topic
     */
    private String topic;
    /**
     * consumerid
     */
    private String id;

    private KafkaConsumerDemo(String topic, String groupID, String id) {
        this.topic = topic;
        this.groupID = groupID;
        this.id = id;

        Properties properties = new Properties();
        // ./bin/kafka-console-consumer.sh
        //--bootstap-server localhost:9092
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //groupid
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Integer, String> consumerRecord = kafkaConsumer.poll(1000);
            for (ConsumerRecord<Integer, String> record : consumerRecord) {
                System.out.println("topic --> " + this.topic + "\n | groupID --> " + this.groupID + "\n | consumerid --> " + this.id + "\n | message receive:" + record.value() + "\n | offset --> " + record.offset());
                kafkaConsumer.commitAsync();
            }
        }
    }

    /**
     * @param args
     * topic --> tt
     *  | groupID --> group_2
     *  | consumerid --> consumer_0
     *  | message receive:message_0
     *  | offset --> 22
     * topic --> tt
     *  | groupID --> group_0
     *  | consumerid --> consumer_0
     *  | message receive:message_0
     *  | offset --> 22
     * topic --> tt
     *  | groupID --> group_1
     *  | consumerid --> consumer_0
     *  | message receive:message_0
     *  | offset --> 22
     * topic --> tt
     *  | groupID --> group_1
     *  | consumerid --> consumer_0
     *  | message receive:message_1
     *  | offset --> 23
     * topic --> tt
     *  | groupID --> group_0
     *  | consumerid --> consumer_0
     *  | message receive:message_1
     *  | offset --> 23
     * topic --> tt
     *  | groupID --> group_2
     *  | consumerid --> consumer_0
     *  | message receive:message_1
     *  | offset --> 23
     * topic --> tt
     *  | groupID --> group_0
     *  | consumerid --> consumer_0
     *  | message receive:message_2
     *  | offset --> 24
     * topic --> tt
     *  | groupID --> group_1
     *  | consumerid --> consumer_0
     *  | message receive:message_2
     *  | offset --> 24
     * topic --> tt
     *  | groupID --> group_2
     *  | consumerid --> consumer_0
     *  | message receive:message_2
     *  | offset --> 24
     */
    public static void main(String[] args) {
        //3 group
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                new KafkaConsumerDemo("tt", "group_" + i, "consumer_" + j).start();
            }
        }
    }
}
