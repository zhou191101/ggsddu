package com.ggsddu.utils;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.zookeeper.ZKUtil;

import java.util.Properties;

/**
 * @author zhoup
 */
public class TopicProcessor {

	private static final String ZK_CONNECT = "localhost:32769";
	private static final int SESSION_TIME_OUT = 30000;
	private static final int CONNECT_OUT = 30000;

	public static void createTopic(String topicName, int partitionNumber, int replicaNumber, Properties properties){
		ZkUtils zkUtils = null;

		try{
			zkUtils = ZkUtils.apply(ZK_CONNECT, SESSION_TIME_OUT, CONNECT_OUT, JaasUtils.isZkSecurityEnabled());
			if(!AdminUtils.topicExists(zkUtils, topicName)){
				AdminUtils.createTopic(zkUtils, topicName, partitionNumber, replicaNumber, properties,
									   AdminUtils.createTopic$default$6());

			}
		} finally{
			zkUtils.close();
		}

	}


	public static void main(String[] args){
		TopicProcessor.createTopic("test1", 1, 1, new Properties());
	}

}
