/**   
 * Copyright © 2015 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech03Thread
 * 描述信息: 
 * 创建日期：2015年12月17日 上午11:54:23 
 * @author malitao
 * @version 
 */
package t3pool.example1;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/** 
 *  此为系统用用到的
 * 创建日期：2015年12月17日 上午11:54:23 
 * @author malitao
 */
public class PoolBean {  
	public PoolBean(){
		System.out.println("PoolBean初始化");
	}
//	static Executor executor1 = Executors.newSingleThreadExecutor();
	public static Executor executor = Executors.newSingleThreadScheduledExecutor();
//	static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	static {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						System.out.println(Thread.currentThread().getName()+"： 运行一个线程操作");
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});
	}
	
	public static void fun(){
		System.out.println("fun executor");
	}
}
