package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public class Mocha extends CondimentDecorator {
	private Beverage beverage;

	Mocha(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Mocha";
	}

	@Override
	double cost() {
		return beverage.cost() + 1.0;
	}
}
