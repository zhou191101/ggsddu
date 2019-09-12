package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public class Squack implements QuackBehavior {
	@Override
	public void quack() {
		System.out.println("Squack");
	}
}
