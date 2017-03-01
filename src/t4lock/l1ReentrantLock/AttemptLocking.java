/**   
 * Copyright © 2016 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech03Thread
 * 描述信息: 
 * 创建日期：2016年1月28日 下午4:02:58 
 * @author malitao
 * @version 
 */
package t4lock.l1ReentrantLock;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/** 
 *  
 * 创建日期：2016年1月28日 下午4:02:58 
 * @author malitao
 */
/**
 * 以下是ReentrantLock中断机制的一个代码实现、如果换成synchronized就会出现死锁
 * 参考：http://hyxw5890.iteye.com/blog/1578597 创建日期：2016年1月28日 下午4:03:25
 * 
 * @author malitao
 */
// : concurrency/AttemptLocking.java
public class AttemptLocking {
	private ReentrantLock lock = new ReentrantLock();

	public void untimed() {
		boolean captured = lock.tryLock();
		try {
			System.out.println("tryLock(): " + captured);
		} finally {
			if (captured)
				lock.unlock();
		}
	}

	public void timed() {
		boolean captured = false;
		try {
			captured = lock.tryLock(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
		} finally {
			if (captured)
				lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final AttemptLocking al = new AttemptLocking();
		al.untimed(); // True -- 可以成功获得锁
		al.timed(); // True --可以成功获得锁
		// 新创建一个线程获得锁并且不释放
		new Thread() {
			{
				setDaemon(true);
			}

			public void run() {
				al.lock.lock();
				System.out.println("acquired");
			}
		}.start();
		Thread.sleep(100);// 保证新线程能够先执行
		al.untimed(); // False -- 马上中断放弃
		al.timed(); // False -- 等两秒超时后中断放弃
	}
}
