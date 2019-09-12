package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public class RubberDuck extends Duck {
	RubberDuck() {
		setFlyBehavior(new FlyNoWay());
		setQuackBehavior(new Quack());
	}

	@Override
	void display() {
		System.out.println("I'm rubber duck ...");

	}
}
