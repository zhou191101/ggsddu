package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public class Milk extends CondimentDecorator {
	private Beverage beverage;

	Milk(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Milk";
	}

	@Override
	double cost() {
		return 2.0 + beverage.cost();
	}
}
