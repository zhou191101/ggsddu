package com.ggsddu.observer;

/**
 * @author zhoup
 */
public interface Subject {

	void registerObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObserver();

}
