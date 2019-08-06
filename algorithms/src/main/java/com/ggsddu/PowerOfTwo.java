package com.ggsddu;

/**
 * @author zhoup
 */
public class PowerOfTwo {
	public static void main(String[] args){

		countBits(16);
	}

	public static int[] countBits(int num) {
		int[] rs = new int[num+1];
		for(int i = 1;i<=num;i++){
			rs[i] += rs[i & (i-1)] +1;
			System.out.println(rs[i]);
		}
		return rs;
	}
}
