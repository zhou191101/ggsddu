package com.ggsddu.fatory.sample;

/**
 * @author zhoup
 */
public class Main {
	public static void main(String[] args) {

		PizzaStore chicagoPizzaStore = new ChicagoPizzaStore();
		chicagoPizzaStore.orderPizza("cheese");
		System.out.println("===================");
		PizzaStore NYPizzaStore = new NYPizzaStore();
		NYPizzaStore.orderPizza("veggie");
	}
}
