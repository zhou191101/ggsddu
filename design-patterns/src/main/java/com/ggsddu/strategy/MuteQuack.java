package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public class MuteQuack implements QuackBehavior {
	@Override
	public void quack() {
		System.out.println("MuteQuack");
	}
}
