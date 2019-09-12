package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public class HouseBlend extends Beverage {

	HouseBlend() {
		setDescription("House Blend Coffee");
	}

	@Override
	double cost() {
		return 8.0;
	}
}
