/**   
 * Copyright © 2015 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech03Thread
 * 描述信息: 
 * 创建日期：2015年12月17日 下午3:34:52 
 * @author malitao
 * @version 
 */
package t3pool.example3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Executors 提供的线程池方案
 * 核心还是对ThreadPoolExecutor的包装
 * newFixedThreadPool newSingleThreadExecutor newCachedThreadPool
 * newScheduledThreadPool newSingleThreadScheduledExecutor
 * 创建日期：2015年12月17日 下午3:34:52
 * 
 * @author malitao
 */
public class ExecutorsDemo {

	public static void main(String[] args) {
		// 一共可分为 5 种
		// 不可延时线程池： 固定、单一、可变
		// demoPools();
		// 可延时线程池： 固定、单一
		demoPools2();
	}
	public static void demoPools() {
		// 一、固定大小的线程池，newFixedThreadPool：
		ExecutorService pool = Executors.newFixedThreadPool(5);
		// 创建一个可重用固定线程数的线程池
		// 二、单任务线程池，newSingleThreadExecutor：
		// ExecutorService pool = Executors.newSingleThreadExecutor();
		// 三、可变尺寸的线程池，newCachedThreadPool：
//		ExecutorService pool = Executors.newCachedThreadPool();// 这种方式的特点是：可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
		// 创建线程
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		Thread t4 = new MyThread();
		Thread t5 = new MyThread();
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		// 关闭线程池
		pool.shutdown();
	}
	public static void demoPools2() {
		// 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
		// 四、可延迟的固定池 -可进入无限循环线程获取中
		// ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
		// 五、可延迟的单任务池
		ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();

		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		Thread t4 = new MyThread();
		Thread t5 = new MyThread();
		// 将线程放入池中进行执行
		pool.execute(t1);
		// 使用延迟执行风格的方法
		pool.schedule(t2, 10, TimeUnit.MILLISECONDS);
		pool.schedule(t3, 10, TimeUnit.MILLISECONDS);
		// 关闭线程池
		// pool.shutdown();
		// 使用延迟和频率执行的风格
		pool.scheduleAtFixedRate(t4, 1, 5, TimeUnit.MILLISECONDS);
		pool.scheduleWithFixedDelay(t5, 2, 10, TimeUnit.MILLISECONDS);

	}
}

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "正在执行。。。");
	}
}

/**
 * 说明：
 * 参考：http://blog.csdn.net/coding_or_coded/article/details/6856014
 * Java5的线程池分好多种：具体的可以分为两类，固定尺寸的线程池、可变尺寸连接池。
 * 在使用线程池之前，必须知道如何去创建一个线程池，在Java5中，需要了解的是java.util.concurrent.Executors类的API，这个类提供大量创建连接池的静态方法，是必须掌握的。 
 * 一、固定大小的线程池，newFixedThreadPool：
 * 二、单任务线程池，newSingleThreadExecutor：仅仅是把上述代码中的ExecutorService pool = Executors.newFixedThreadPool(2)改为ExecutorService pool = Executors.newSingleThreadExecutor(); 
 * 三、可变尺寸的线程池，newCachedThreadPool：与上面的类似，只是改动下pool的创建方式：ExecutorService pool = Executors.newCachedThreadPool();
 * 四、延迟连接池，newScheduledThreadPool： 
 * 五：单任务延迟连接池(和上面类似，就不写了)。 
 * 当然我们也可以自定义线程池
 **/
