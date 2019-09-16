package com.ggsddu.regex;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author zhoup
 */
public class NumLengthRegex {

	private static final Pattern p = Pattern
			.compile(
					"^000860[1][3-9][0-9]{9}$|^0000010[0-9]{10}$|^00088600[0-9]{9}|^000852000([6|9])[0-9]{7}|^00085300000([6|8])[0-9]{5}");

	public static void main(String[] args) {

		String num1 = "00086018080587865";
		String num2 = "00000105613662033";
		String num3 = "00088600935921609";
		String num4 = "00085200098623039";
		String num5 = "00085300000824372";

		System.out.println(p.matcher(num1).matches());
		System.out.println(p.matcher(num2).matches());
		System.out.println(p.matcher(num3).matches());
		System.out.println(p.matcher(num4).matches());
		System.out.println(p.matcher(num5).matches());

	}
}
