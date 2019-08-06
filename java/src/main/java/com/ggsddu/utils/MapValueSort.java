package com.ggsddu.utils;

import java.util.*;

/**
 * @author zhoup
 */
public class MapValueSort {


	public static LinkedHashMap<String, String> sortMapByValue(Map<String, String> map){
		LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();

		if(map != null && !map.isEmpty()){
			List<Map.Entry<String, String>> entryList = new ArrayList<>(map.entrySet());
			Collections.sort(entryList, Comparator.comparing(Map.Entry::getValue));
			Iterator<Map.Entry<String, String>> iterator = entryList.iterator();
			Map.Entry<String, String> tmpEntry;
			while(iterator.hasNext()){
				tmpEntry = iterator.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}
}

