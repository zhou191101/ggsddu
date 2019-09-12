package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public class Quack implements QuackBehavior {
	@Override
	public void quack() {
		System.out.println("Quack");
	}
}
