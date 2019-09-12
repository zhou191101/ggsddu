package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public class Espresso extends Beverage {

	Espresso(){
		setDescription("Espresso");
	}

	@Override
	double cost() {
		return 7.0;
	}
}
