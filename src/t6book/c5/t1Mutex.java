package t6book.c5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义独占锁-需要重写队列同步器（原理是FIFO）
 * 互斥锁、独占锁
 * @author malitao
 *
 */
public class t1Mutex implements Lock{

	private static class Sync extends AbstractQueuedSynchronizer{
		private static final long serialVersionUID = 1L;
		//返回状态 1是占有 0是释放
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}
		
		//当状态为0的时候获取锁
		@Override
		protected boolean tryAcquire(int acquires) {
			assert acquires == 1;
			if(compareAndSetState(0, 1)){
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		
		//释放锁，将状态置为0
		@Override
		protected boolean tryRelease(int release) {
			assert release ==1 ;
			if(getState() == 0)
				throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		};
		
		//返回一个condition，每个都包含一个condition队列
		Condition newCondition(){
			return new ConditionObject();
		}
	}
	//将操作代理到Sync上
	private final Sync sync = new Sync(); 
	
	@Override
	public void lock() {
		sync.acquire(1);
	}
	@Override
	public void unlock() {
		sync.release(1);
	}
	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(timeout));
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
	
	public boolean isLocked(){
		return sync.isHeldExclusively();
	}
	
	//
	public boolean hasQueueThreads(){
		return sync.hasQueuedThreads();
	}
}
