package com.ggsddu.collection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoup
 */
public class ConcurrentHashMapTest {
	public static void main(String[] args){

		ConcurrentHashMap<String,String> hashMap = new ConcurrentHashMap<>();

		hashMap.put("aa","11");
		hashMap.put("bb","22");
		hashMap.put("aa","33");


	}
}
