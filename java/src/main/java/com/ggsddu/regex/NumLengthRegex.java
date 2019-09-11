package com.ggsddu.regex;

import java.util.regex.Pattern;

/**
 * @author zhoup
 */
public class NumLengthRegex {

	private static final Pattern pattern = Pattern.compile("\\d{11}|\\d{17}");

	public static void main(String[] args){

		String num1 = "18081587866";
		String num2 = "18080187867123456";

		System.out.println(pattern.matcher(num1).matches());
		System.out.println(pattern.matcher(num2).matches());


	}
}
