package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public class Whip extends CondimentDecorator {

	private Beverage beverage;

	Whip(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Whip";
	}

	@Override
	double cost() {
		return 2.0 + beverage.cost();
	}
}
