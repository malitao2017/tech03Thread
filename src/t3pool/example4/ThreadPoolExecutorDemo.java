/**   
 * Copyright © 2016 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech03Thread
 * 描述信息: 
 * 创建日期：2016年1月26日 上午11:21:07 
 * @author malitao
 * @version 
 */
package t3pool.example4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** 
 *  自定义线程池
 *  ThreadPoolExecutor 和 Executors 的关系
 *  方法中建立一个核心线程数为30个，缓冲队列有10个的线程池。每个线程任务，执行时会先睡眠0.1秒，保证提交40个任务时没有任务被执行完，这样提交第41个任务是，会交给CustomRejectedExecutionHandler 类来处理。
 * 创建日期：2016年1月26日 上午11:21:07 
 * @author malitao
 */
public class ThreadPoolExecutorDemo {

	private ThreadPoolExecutor pool = null;

	/**
	 * 线程池初始化方法
	 * 
	 * corePoolSize 核心线程池大小----10 maximumPoolSize 最大线程池大小----30 keepAliveTime
	 * 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit TimeUnit
	 * keepAliveTime时间单位----TimeUnit.MINUTES workQueue 阻塞队列----new
	 * ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列 threadFactory 新建线程工厂----new
	 * CustomThreadFactory()====定制的线程工厂 rejectedExecutionHandler
	 * 当提交任务数超过maxmumPoolSize+workQueue之和时,
	 * 即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
	 * 任务会交给RejectedExecutionHandler来处理
	 */
	public void init() {
		pool = new ThreadPoolExecutor(10, 30, 30, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10),
				new CustomThreadFactory(), new CustomRejectedExecutionHandler());
	}

	public void destory() {
		if (pool != null) {
			pool.shutdownNow();
		}
	}

	public ExecutorService getCustomThreadPoolExecutor() {
		return this.pool;
	}

	private class CustomThreadFactory implements ThreadFactory {

		private AtomicInteger count = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			String threadName = ThreadPoolExecutorDemo.class.getSimpleName() + count.addAndGet(1);
			System.out.println(threadName);
			t.setName(threadName);
			return t;
		}
	}

	private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			// 记录异常
			// 报警处理等
			System.out.println("error.............");
		}
	}

	// 测试构造的线程池
	/**
	 * 方法中建立一个核心线程数为30个，缓冲队列有10个的线程池。每个线程任务，执行时会先睡眠0.1秒，保证提交40个任务时没有任务被执行完，这样提交第41个任务是，会交给CustomRejectedExecutionHandler 类来处理。
	 * @param args
	 * 创建日期：2016年1月26日 上午11:41:46 
	 * @author malitao
	 */
	public static void main(String[] args) {
		ThreadPoolExecutorDemo exec = new ThreadPoolExecutorDemo();
		// 1.初始化
		exec.init();

		ExecutorService pool = exec.getCustomThreadPoolExecutor();
		for (int i = 1; i < 100; i++) {
			System.out.println("提交第" + i + "个任务!");
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("running=====");
				}
			});
		}

		// 2.销毁----此处不能销毁,因为任务没有提交执行完,如果销毁线程池,任务也就无法执行了
		// exec.destory();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
//  参考：http://825635381.iteye.com/blog/2184680
//	ThreadPoolExecutor机制 
//	一、概述 
//	1、ThreadPoolExecutor作为java.util.concurrent包对外提供基础实现，以内部线程池的形式对外提供管理任务执行，线程调度，线程池管理等等服务； 
//	2、Executors方法提供的线程服务，都是通过参数设置来实现不同的线程池机制。 
//	3、先来了解其线程池管理的机制，有助于正确使用，避免错误使用导致严重故障。同时可以根据自己的需求实现自己的线程池 
//	二、核心构造方法讲解 
//	下面是ThreadPoolExecutor最核心的构造方法 
//	public ThreadPoolExecutor(int corePoolSize,
//            int maximumPoolSize,
//            long keepAliveTime,
//            TimeUnit unit,
//            BlockingQueue<Runnable> workQueue,
//            ThreadFactory threadFactory,
//            RejectedExecutionHandler handler) {
//if (corePoolSize < 0 ||
//maximumPoolSize <= 0 ||
//maximumPoolSize < corePoolSize ||
//keepAliveTime < 0)
//throw new IllegalArgumentException();
//if (workQueue == null || threadFactory == null || handler == null)
//throw new NullPointerException();
//this.corePoolSize = corePoolSize;
//this.maximumPoolSize = maximumPoolSize;
//this.workQueue = workQueue;
//this.keepAliveTime = unit.toNanos(keepAliveTime);
//this.threadFactory = threadFactory;
//this.handler = handler;
//}
//	构造方法参数讲解 
//	参数名	作用
//	corePoolSize	核心线程池大小
//	maximumPoolSize	最大线程池大小
//	keepAliveTime	线程池中超过corePoolSize数目的空闲线程最大存活时间；可以allowCoreThreadTimeOut(true)使得核心线程有效时间
//	TimeUnit	keepAliveTime时间单位
//	workQueue	阻塞任务队列
//	threadFactory	新建线程工厂
//	RejectedExecutionHandler	当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
//
//
//	重点讲解： 
//	其中比较容易让人误解的是：corePoolSize，maximumPoolSize，workQueue之间关系。 
//
//	1.当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程。 
//	2.当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行 
//	3.当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建新线程执行任务 
//	4.当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理 
//	5.当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程 
//	6.当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭 
//
//	线程管理机制图示： 
//	点击查看原始大小图片
//	三、Executors提供的线程池配置方案 
//	1、newFixedThreadPool ：构造一个固定线程数目的线程池，配置的corePoolSize与maximumPoolSize大小相同，同时使用了一个无界LinkedBlockingQueue存放阻塞任务，因此多余的任务将存在再阻塞队列，不会由RejectedExecutionHandler处理 
//	2、newCachedThreadPool ：构造一个缓冲功能的线程池，配置corePoolSize=0，maximumPoolSize=Integer.MAX_VALUE，keepAliveTime=60s,以及一个无容量的阻塞队列 SynchronousQueue，因此任务提交之后，将会创建新的线程执行；线程空闲超过60s将会销毁 
//	3、newSingleThreadExecutor ：构造一个只支持一个线程的线程池，配置corePoolSize=maximumPoolSize=1，无界阻塞队列LinkedBlockingQueue；保证任务由一个线程串行执行 
//	4、newScheduledThreadPool newSingleThreadScheduledExecutor ：构造有定时功能的线程池，配置corePoolSize，无界延迟阻塞队列DelayedWorkQueue；有意思的是：maximumPoolSize=Integer.MAX_VALUE，由于DelayedWorkQueue是无界队列，所以这个值是没有意义的 
//	四、定制属于自己的线程池 
//	方法中建立一个核心线程数为30个，缓冲队列有10个的线程池。每个线程任务，执行时会先睡眠0.1秒，保证提交40个任务时没有任务被执行完，这样提交第41个任务是，会交给CustomRejectedExecutionHandler 类来处理。

