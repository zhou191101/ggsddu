package com.ggsddu.decorator;

/**
 * @author zhoup
 */
public abstract class Beverage {

	private String description = "No description";

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {

		return this.description;
	}

	abstract double cost();
}
