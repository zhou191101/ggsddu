package com.ggsddu.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author zhoup
 */
public class MessageProducer {

    private static final String TOPIC = "test";
    private static final String BROKER_LIST = "localhost:9092";
    private static KafkaProducer<String, String> producer;

    static {
        Properties configs = initConfig();
        producer = new KafkaProducer<>(configs);
    }

    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    public static void main(String[] args) {

        try {
            String message = "hello world";
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
            producer.send(record, (recordMetadata, e) -> {
                if (null == e) {
                    System.out.println("perfect!");
                }
                if (null != recordMetadata) {
                    System.out.println(
                            "offset: " + recordMetadata.offset() + "; partition: " + recordMetadata.partition());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
