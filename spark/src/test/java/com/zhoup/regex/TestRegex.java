package com.zhoup.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoup
 */
public class TestRegex {
	public static void main(String[] args){
		String regex = args[0];
		String num = "18080587865111111";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(num);

		System.out.println(matcher.matches());


	}
}
