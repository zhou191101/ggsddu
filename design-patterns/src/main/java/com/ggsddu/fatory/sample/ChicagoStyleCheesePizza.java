package com.ggsddu.fatory.sample;

/**
 * @author zhoup
 */
public class ChicagoStyleCheesePizza extends Pizza {
	public ChicagoStyleCheesePizza() {
		setName("Chicago Style Sauce and Cheese Pizza");
		setDough("Extra Thick Crust Dough");
		setSauce("Plum Tomato Sauce");
		toppings.add("Shredded Mozzarella Cheese");
	}

	@Override
	public void cut() {
		System.out.println("Cutting the pizza into square slices");
	}
}
