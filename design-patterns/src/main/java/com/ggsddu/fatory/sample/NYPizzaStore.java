package com.ggsddu.fatory.sample;

import java.util.Objects;

/**
 * @author zhoup
 */
public class NYPizzaStore extends PizzaStore {
	@Override
	protected Pizza createPizza(String type) {
		if (Objects.equals(type, "cheese")) {
			return new NYStyleCheesePizza();
		} else if (Objects.equals(type, "veggie")) {
			return new NYStyleVeggiePizza();
		} else {
			throw new IllegalArgumentException("No such  type pizza");
		}
	}
}
