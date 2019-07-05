package com.ggsddu.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author zhoup
 */
public class Mutex implements Lock {


	private static class Sync extends AbstractQueuedSynchronizer {
		@Override
		protected boolean isHeldExclusively(){
			return getState() == 1;
		}

		@Override
		protected boolean tryAcquire(int arg){
			if(compareAndSetState(0, 1)){
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}

			setExclusiveOwnerThread(null);

			setState(0);


			return true;
		}

		Condition condition(){
			return new ConditionObject();
		}


		@Override
		protected boolean tryRelease(int arg){

			if(getState() == 0){
				throw new IllegalMonitorStateException();
			}

			return super.tryRelease(arg);
		}
	}

	private final Sync sync = new Sync();

	@Override
	public void lock(){
		sync.acquire(1);

	}

	@Override
	public void lockInterruptibly() throws InterruptedException{

		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock(){

		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException{
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock(){

		sync.release(1);
	}

	@Override
	public Condition newCondition(){
		return sync.condition();
	}
}
