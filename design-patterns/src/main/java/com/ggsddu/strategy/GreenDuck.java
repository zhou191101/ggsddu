package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public class GreenDuck extends Duck {

	GreenDuck() {
		setQuackBehavior(new MuteQuack());
		setFlyBehavior(new FlyWithWings());
	}

	@Override
	void display() {
		System.out.println("I'm green duck ...");
	}
}
