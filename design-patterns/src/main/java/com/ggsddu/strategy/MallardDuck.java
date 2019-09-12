package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public class MallardDuck extends Duck {

	MallardDuck() {
		setFlyBehavior(new FlyWithWings());
		setQuackBehavior(new Quack());
	}

	@Override
	void display() {

		System.out.println("I'm mallard duck ...");
	}
}
