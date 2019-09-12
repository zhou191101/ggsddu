package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public class FlyNoWay implements FlyBehavior {
	@Override
	public void fly() {
		System.out.println("I can't fly ...");
	}
}
