package com.ggsddu.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoup
 */
public class ThreadPoolTest {
	public static void main(String[] args){

		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
															 new ArrayBlockingQueue<Runnable>(1024),
															 new ThreadPoolExecutor.AbortPolicy());
	}
}
