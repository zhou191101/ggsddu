package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public class DarkRoast extends Beverage {

	DarkRoast() {
		setDescription("Dark Roast Coffee");
	}

	@Override
	double cost() {
		return 9.0;
	}
}
