package com.ggsddu.observer;

/**
 * @author zhoup
 */
public class WeatherStation {

	public static void main(String[] args) {

		WeatherData weatherData = new WeatherData();

		CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
		weatherData.setMeasurements(81, 65, 30.4f);
	}

}
