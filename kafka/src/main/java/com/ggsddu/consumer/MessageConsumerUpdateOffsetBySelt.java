package com.ggsddu.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhoup
 */
public class MessageConsumerUpdateOffsetBySelt {

    private static final String TOPIC = "test";
    private static final String BROKER_LIST = "localhost:9092";
    private static KafkaConsumer<String, String> kafkaConsumer = null;


    static {
        Properties properties = initConfig();
        kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC));
    }

    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "ccc");
        properties.setProperty("auto.offset.reset", "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return properties;
    }

    public static void main(String[] args) {
        Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
        int count = 0;
        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
                // System.out.println(records.isEmpty());
                for (ConsumerRecord record : records) {
                    System.out.println(record.value());
                    map.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, "no metadata"));
                    if (count % 2 == 0) {
                        kafkaConsumer.commitAsync(map, null);
                    }
                    count++;
                }

            }
        } finally {
            kafkaConsumer.close();
        }
    }
}
