package com.ggsddu.fatory.sample;

/**
 * @author zhoup
 */
public class ChicagoStyleVeggiePizza extends Pizza {
	public ChicagoStyleVeggiePizza() {
		setName("Chicago Style Sauce and Veggie Pizza");
		setDough("Extra Thick Crust Dough");
		setSauce("Plum Tomato Sauce");
		toppings.add("Shredded Mozzarella Cheese");
	}

	@Override
	public void cut() {
		System.out.println("Cutting the pizza into square slices");
	}
}
