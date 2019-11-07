package com.ggsddu.lambda;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.IntSummaryStatistics;
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
		
	}
}
