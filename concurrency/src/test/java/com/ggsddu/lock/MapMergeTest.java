package com.ggsddu.lock;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoup
 */
public class MapMergeTest {
	public static void main(String[] args){

		Map<String , Set<String>> map = new ConcurrentHashMap<>();

		Set<String> set = new HashSet<>();

		set.add("aa");
		set.add("aa");
		set.add("bb");
		map.put("1",set);
		map.put("2",set);
		Set<String> set2 = new HashSet<>();
		set2.add("cc");
		map.merge("3",set2,(v1,v2)->{
			v1.addAll(v2);
			return  v1;
		});
		System.out.println(map);
 	}
}
