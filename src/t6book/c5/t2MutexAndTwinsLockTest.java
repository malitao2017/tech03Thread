package t6book.c5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class t2MutexAndTwinsLockTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {
		//独占锁测试
		Lock lock1 = new t1Mutex();
		test(lock1);
		System.out.println("独占锁测试:一个一个线程出现输出");
		
		//共享锁测试
		Lock lock2 = new t2TwinsLock();
//		test(lock2);
		System.out.println("共享锁测试： 两个共享线程，成对输出");
	}
	
	public static void test(final Lock lock) throws InterruptedException{
		class Worker extends Thread{
			@Override
			public void run() {
				lock.lock();
				try{
					TimeUnit.SECONDS.sleep(1);
					System.out.println(Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			Worker worker = new Worker();
			worker.setDaemon(true);
			worker.start();
		}
		for (int i = 0; i < 20; i++) {
			TimeUnit.SECONDS.sleep(1);
			System.out.println();
		}
	}
}
