package com.ggsddu.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author zhoup
 */
public class MessageConsumer {

	private static final String TOPIC = "test1";
	private static final String BROKER_LIST = "localhost:9092";
	private static KafkaConsumer<String, String> kafkaConsumer = null;


	static{
		Properties properties = initConfig();
		kafkaConsumer = new KafkaConsumer<>(properties);
		kafkaConsumer.subscribe(Arrays.asList(TOPIC));
	}

	private static Properties initConfig(){
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "aa");
		//	properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "aa");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		return properties;
	}

	public static void main(String[] args){
		try{
			while(true){

				ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));

				System.out.println(records.isEmpty());
				for(ConsumerRecord record : records){
					System.out.println(record.value());
				}

			}
		} finally{
			kafkaConsumer.close();
		}
	}
}
