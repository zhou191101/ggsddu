package com.ggsddu.lambda;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhoup
 */
public class StreamTest {
    public static void main(String[] args) {

        String str = Stream.of("A", "B", "C").collect(Collectors.joining(":"));
        System.out.println(str);
        Double avgLength = Stream.of("hello", "world", "china", "shanghai").collect(Collectors.averagingInt(String::length));
        System.out.println(avgLength);
        IntSummaryStatistics summaryStatistics = Stream.of("a", "b", "cd").collect(Collectors.summarizingInt(String::length));
        System.out.println(summaryStatistics);

        HashMap<Object, Object> map = new HashMap<>();
    }

    public int maxLength(List<String> arr) {
        TreeMap<Integer, String> rs = new TreeMap<>();
        int c = 0;
        while (c < arr.size()) {
            String current = arr.get(0);

            for (int i = 1; i < arr.size(); i++) {
                String a = arr.get(i);
                for (Character aa : a.toCharArray()) {
                    if (current.contains(aa.toString())) {
                        break;
                    }
                }
            }
            c++;
        }
        return rs.firstKey();
    }
}
