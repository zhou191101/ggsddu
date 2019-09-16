package com.ggsddu.fatory.sample;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoup
 */
public abstract class Pizza {

	private String name;
	private String dough;
	private String sauce;
	List<String> toppings = new ArrayList<>();


	void prepare() {
		System.out.println("Preparing " + name);
		System.out.println("Tossing dough ... ");
		System.out.println("Adding sauce");
		System.out.println("Adding toppings...");
		for (String topping: toppings) {
			System.out.println(" " + topping);
		}
	}

	public void bake() {
		System.out.println("Bake for 25 minutes at 350");
	}

	public void cut() {
		System.out.println("Cutting the pizza into diagonal slices");
	}

	public void box() {
		System.out.println("Place pizza in offical PizzaStore box");
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDough(String dough) {
		this.dough = dough;
	}

	public void setSauce(String sauce) {
		this.sauce = sauce;
	}
}
