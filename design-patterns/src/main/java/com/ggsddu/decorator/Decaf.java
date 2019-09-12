package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public class Decaf extends Beverage {

	Decaf(){
		setDescription("Decaf");
	}

	@Override
	double cost() {
		return 11.0;
	}
}
