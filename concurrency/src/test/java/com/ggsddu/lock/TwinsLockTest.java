package com.ggsddu.lock;

import java.util.concurrent.locks.Lock;

/**
 * @author zhoup
 */
public class TwinsLockTest {

	public static void main(String[] args){

		final Lock lock = new TwinsLock();

		// 启动10个线程
		for(int i = 0; i < 10; i++){
			Thread t = new Thread(() -> {
				while(true){
					lock.lock();
					try{
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName());
						Thread.sleep(1000);
					} catch(InterruptedException e){
						e.printStackTrace();
					} finally{
						lock.unlock();
					}
				}
			});
			t.setDaemon(true);
			t.start();
		}

		// 每隔一秒钟换一行
		for(int i = 0; i < 10; i++){
			try{
				Thread.sleep(1000);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println();
		}

	}
}
