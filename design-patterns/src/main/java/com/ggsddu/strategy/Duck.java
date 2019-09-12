package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public abstract class Duck {

	private FlyBehavior flyBehavior;
	private QuackBehavior quackBehavior;

	abstract void display();

	public void performFly() {
		flyBehavior.fly();
	}


	public void performQuack() {
		quackBehavior.quack();
	}


	public void setFlyBehavior(FlyBehavior flyBehavior) {
		this.flyBehavior = flyBehavior;
	}

	public void setQuackBehavior(QuackBehavior quackBehavior) {
		this.quackBehavior = quackBehavior;
	}
}
