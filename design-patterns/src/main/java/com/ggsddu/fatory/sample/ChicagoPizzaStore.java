package com.ggsddu.fatory.sample;

import java.util.Objects;

/**
 * @author zhoup
 */
public class ChicagoPizzaStore extends PizzaStore {
	@Override
	protected Pizza createPizza(String type) {
		if (Objects.equals(type, "cheese")) {
			return new ChicagoStyleCheesePizza();
		} else if (Objects.equals(type, "veggie")) {
			return new ChicagoStyleVeggiePizza();
		} else {
			throw new IllegalArgumentException("No such type pizza");
		}
	}
}
