package com.ggsddu.singleton;

/**
 * @author zhoup
 */
public class Singleton {

	private static volatile Singleton singleton;

	private Singleton() {
	}

	public Singleton getInstance() {

		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
}
