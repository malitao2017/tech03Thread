/**   
 * Copyright © 2015 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech03Thread
 * 描述信息: 
 * 创建日期：2015年12月17日 下午2:17:50 
 * @author malitao
 * @version 
 */
package t3pool.example2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 参考： http://hbiao68.iteye.com/blog/1929245
 * 系统启动一个新线程的成本是比较高的，因为它涉及与操作系统交互。在这种情形下，使用线程池可以很好地提高性能，尤其是当程序中需要创建大量生存周期很短的线程时，
 * 更应该考虑使用线程池。
 * 
 * 使用线程池可以有效地控制系统中并发线程的数量，当系统中包含大量并发线程时，会导致系统性能剧烈下降，甚至导致JVM崩溃，
 * 而线程池的最大线程数参数可以控制系统中并发线程数不超过此数。
 * 
 * 从JAVA5开始新增了一个Executors工具类来产生线程池，它有如下几个静态工厂方法来创建线程池。 强烈建议程序员使用较为方便的 Executors
 * 工厂方法 Executors.newCachedThreadPool()（无界线程池，可以进行自动线程回收）、
 * Executors.newFixedThreadPool(int)（固定大小线程池）
 * Executors.newSingleThreadExecutor()（单个后台线程）
 * Executors.newScheduledThreadPool(int corePoolSize) 可以指定延迟后执行线程任务。
 * 
 * ExecutorService代表尽快执行线程的线程池（只要线程池中有空闲的线程，就立即执行线程任务），
 * 程序只要将一个Runnable对象或Callable对象（代表线程任务）提交给该线程，该线程就会尽快执行该任务
 * 
 * 当用完一个线程池后，应该嗲用该线程池的shutdown()方法，该方法将启动线程池的关闭序列，调用shutdown()方法后的线程池不再接受新任务，
 * 但将以前所有已提交任务执行完。当线程池中的所有任务都执行完成后，池中的所有线程都会死亡；
 * 另外也可以调用线程池中的shutdownNow()方法来关闭线程池，该方法试图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行任务列表。
 * 
 * 创建日期：2015年12月17日 下午2:17:50
 * 
 * @author malitao
 * 
 */
public class ThreadPoolDemo {

	/**
	 * 这里应用了个别的线程池种类，具体的分类，查看：t3pool.example3
	 * 
	 * 继承关系为： Executor - ExecutorService - ScheduledExecutorService -  ScheduledThreadPoolExecutor
	 *
	 * @param args
	 * 创建日期：2016年1月19日 下午4:18:40 
	 * @author malitao
	 */
	public static void main(String[] args) {
		//不可延时的线程池
		// FixedThreadPoolDemo();
		//可延时的线程池
		ScheduledThreadPoolExecutorDemo();
	}

	/**
	 * 固定线程池
	 *
	 * 创建日期：2015年12月17日 下午2:22:32
	 * 
	 * @author malitao
	 */
	public static void FixedThreadPoolDemo() {
		//一、固定大小的线程池
		ExecutorService pool = Executors.newFixedThreadPool(2);// 线程池只有两个
		//二、可变尺寸的线程池
		// ExecutorService pool = Executors.newCachedThreadPool();//线程池需要就建立
		for (int i = 0; i < 10; i++) {
			final int count = i;
			// 过两秒启动线程运行里面的run方法
			pool.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + "----------" + count);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		System.out.println("1关闭前线程状态：" + pool.isShutdown());
		// 等待线程运行完毕之后再停止线程。
		pool.shutdown();
		// 强制停止线程，如果当前线程正在执行，则被强制停止。
		// pool.shutdownNow();
		System.out.println("1关闭后线程状态：" + pool.isShutdown());
	}

	/**
	 * 课指定延后时间
	 *
	 * 创建日期：2015年12月17日 下午2:30:12
	 * 
	 * @author malitao
	 */
	public static void ScheduledThreadPoolExecutorDemo() {
//		 ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(2);//这个是下面的源码实现
		//三、可延迟的固定池
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
		for (int i = 0; i < 10; i++) {
			final int count = i;
			// 过两秒启动线程运行里面的run方法
			pool.schedule(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + "----------" + count);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, 2, TimeUnit.SECONDS);
		}
		System.out.println("2关闭前线程状态：" + pool.isShutdown());
		// 等待线程运行完毕之后再停止线程。
		pool.shutdown();
		// 强制停止线程，如果当前线程正在执行，则被强制停止。
		// pool.shutdownNow();
		System.out.println("2关闭后线程状态：" + pool.isShutdown());
	}
}
