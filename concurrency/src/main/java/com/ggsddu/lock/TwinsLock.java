package com.ggsddu.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author zhoup
 */
public class TwinsLock implements Lock {


	private static class Sync extends AbstractQueuedSynchronizer {


		Sync(int count){
			if(count < 0){
				throw new IllegalArgumentException();
			}
			setState(count);
		}

		@Override
		protected int tryAcquireShared(int arg){
			for(; ; ){

				int current = getState();
				int newState = current - arg;

				if(current < 0 || compareAndSetState(current, newState)){
					return newState;
				}

			}
		}


		@Override
		protected boolean tryReleaseShared(int arg){

			for(; ; ){
				int current = getState();
				int newState = current + arg;
				if(compareAndSetState(current, newState)){
					return true;
				}

			}
		}
	}


	private Sync sync = new Sync(2);

	@Override
	public void lock(){
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException{

	}

	@Override
	public boolean tryLock(){
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException{
		return false;
	}

	@Override
	public void unlock(){
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition(){
		return null;
	}
}
