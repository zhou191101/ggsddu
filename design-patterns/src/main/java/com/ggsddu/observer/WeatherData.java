package com.ggsddu.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoup
 */
public class WeatherData implements Subject {
	private float temp;
	private float humidity;
	private float pressure;
	private List<Observer> observers;

	WeatherData() {
		observers = new ArrayList<>();
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);

	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver() {
		for (Observer observer: observers) {
			observer.update(this.temp, this.humidity, this.pressure);
		}
	}

	public void measurementsChanged() {
		notifyObserver();
	}

	public void setMeasurements(float temp, float humidity, float pressure) {

		this.temp = temp;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}
}
