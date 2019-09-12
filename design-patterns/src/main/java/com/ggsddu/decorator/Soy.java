package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public class Soy extends CondimentDecorator {
	private Beverage beverage;

	Soy(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Soy";
	}

	@Override
	double cost() {
		return 1.5 + beverage.cost();
	}
}
