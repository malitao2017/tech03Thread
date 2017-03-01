package t6book.c5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义共享锁-重写队列同步器
 * 
 * 共享锁
 * 同一时刻允许至多两个线程来访问
 * state： 0两个线程获取了同步资源  1一个线程获取 2没有线程占用
 * 
 */
public class t2TwinsLock implements Lock{
	private static class Sync extends AbstractQueuedSynchronizer{
		
		private static final long serialVersionUID = 1L;
		//本例中同步资源数为2
		public Sync(int count) {
			if(count<=0){
				throw new IllegalArgumentException();
			}
			setState(count);
		}
		
		/**
         * 加
         * 传参为1
         * 能替换的state最小的值为0 
         * 小于0时返回的是-1
         */
		@Override
		protected int tryAcquireShared(int count) {
			for(;;){
				int current = getState();
				int newCount = current - count;
				if(newCount<0 || compareAndSetState(current, newCount))//循环是为了要CAS
					return newCount;
			}
		}
		
		@Override
		protected boolean tryReleaseShared(int count) {
			for(;;){
				int current = getState();
				int newCount = current + count;
				if(compareAndSetState(current, newCount)){
					return true;
				}
			}
		}
		
		//获取condition队列
		Condition newCondition(){
			return new ConditionObject();
		}
	}
	
	private Sync sync = new Sync(2);
	
	@Override
	public void lock() {
		sync.acquireShared(1);
	}
	@Override
	public void unlock() {
		sync.releaseShared(1);
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}
	//state最小为0 
	@Override
	public boolean tryLock() {
		return sync.tryAcquireShared(1) >= 0;
	}
	@Override
	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
		sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
		return true;
	}
	
	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
}
