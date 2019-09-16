package com.ggsddu.fatory.sample;

/**
 * @author zhoup
 */
public abstract class PizzaStore {


	public void orderPizza(String type) {

		Pizza pizza = createPizza(type);
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
	}

	protected abstract Pizza createPizza(String type);
}
