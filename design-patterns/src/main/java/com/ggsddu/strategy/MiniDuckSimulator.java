package com.ggsddu.strategy;

/**
 * @author zhoup
 */
public class MiniDuckSimulator {

	public static void main(String[] args) {

		Duck rubberDuck = new RubberDuck();
		rubberDuck.display();
		rubberDuck.performFly();
		rubberDuck.performQuack();
		Duck greenDuck = new GreenDuck();
		greenDuck.display();
		greenDuck.performFly();
		greenDuck.performQuack();

		Duck mallardDuck = new MallardDuck();

		mallardDuck.display();
		mallardDuck.performFly();
		mallardDuck.performQuack();

	}


}
