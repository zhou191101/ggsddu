package com.ggsddu.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoup
 */
public class MapValueSortTest {

	@Test
	public void test(){
		Map<String, String> map = new HashMap<>();
		map.put("aa", "3");
		map.put("bb", "2");
		map.put("cc", "1");
		map.put("dd", "5");
		System.out.println("unsortedMap:" + map);
		System.out.println("sortedMap:" + MapValueSort.sortMapByValue(map));
	}
}
