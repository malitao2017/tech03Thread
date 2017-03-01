/**   
 * Copyright © 2016 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech03Thread
 * 描述信息: 
 * 创建日期：2016年1月19日 下午4:50:58 
 * @author malitao
 * @version 
 */
package t2way.callable.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/** 
 *   Runnable和Callable的区别:
(1)Runnable是自从java1.1就有了，而Callable是1.5之后才加上去的
(2)Callable规定的方法是call(),Runnable规定的方法是run()
(3)Callable的任务执行后可返回值，而Runnable的任务是不能返回值(是void)
(4)call方法可以抛出异常，run方法不可以
(5)运行Callable任务可以拿到一个Future对象，表示异步计算的结果。它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。通过Future对象可以了解任务执行情况，可取消任务的执行，还可获取执行结果。
(6)加入线程池运行，Runnable使用ExecutorService的execute方法，Callable使用submit方法。
Callable接口也是位于java.util.concurrent包中。Callable接口的定义为：

 * 创建日期：2016年1月19日 下午4:50:58 
 * @author malitao
 */
public class CallableTest {
	public static void main(String[] args) {
		//第一、使用FutureTask
//		call01();
		//第二、线程池的Future
		call02();
		/**
		 * 第三、线程池的CompletionService
		 * 通过这段代码，我们可以根据执行结束的顺序获取对应的结果，而无需维护一个 Future 对象的集合。 
		 */
//		call03();
	}
	//第一、使用FutureTask 执行Callable
	public static void call01()  {
		Callable<String> callable = new CallableDetail(10);
		//FutureTask<V>是一个包装器，它通过接受Callable<String>来创建，它同时实现了Future和Runnable接口  
		FutureTask<String> ft = new FutureTask<>(callable);
		new Thread(ft).start();
		while(!ft.isDone()){
			System.out.println("检查线程执行完成了吗？");
			try {Thread.sleep(1000);}catch (InterruptedException e) {e.printStackTrace();}
		}
		try {System.out.println(ft.get());} catch (Exception e){}
		//FutureTash扮演监督的角色，主线程通过不断询问实现Callable的类对应的线程是否执行完毕，最后可以得到返回的结果。
	}
	/**
	 * 第二、线程池的Future
	 * 下面的例子同时可以阻塞主线程等待子线程完成，但是该方式，如果阻塞的第一个线程很久，可能其他线程已经执行完了，很多情况不太适用
	 * 来源：http://825635381.iteye.com/blog/2184605 
	 * 创建日期：2016年1月26日 下午3:37:58 
	 * @author malitao
	 */
	public static void call02(){
		ExecutorService pool = Executors.newCachedThreadPool();
		//Future 相当于是用来存放Executor 执行的结果的一种容器
		ArrayList<Future<String>> futureList = new ArrayList<Future<String>>();
		for(int i=0;i<3;i++){
			futureList.add(pool.submit(new CallableDetail(i)));
		}
		//修改成轮询
		//get()是阻塞的，get(long timeout,TimeUnit unit) 会阻塞一段时间立刻返回，此时可能没执行完
		List<Integer> over = new ArrayList<Integer>();
		while(true){
			for(int i=0;i<futureList.size();i++){
				Future<String> future = futureList.get(i);
				if(future.isDone()){
					if(!over.contains(i))
						try{System.out.println(future.get());} catch (Exception e){}
					over.add(i);
				}else {
					System.out.println("Future"+i+": result is not yet complete");
				}
			}
			if(over.size() == futureList.size())
				break;
			try{System.out.println("一次轮询完成");Thread.sleep(100);} catch (Exception e){}
		}
		//等线程执行完再停止线程
		pool.shutdown();
	}
	
	/**
	 * 第三、线程池的CompletionService
	 * 上一种情况描述：不过这段代码稍微有点复杂，而且有不足的地方。如果第一个任务耗费非常长的时间来执行，然后其他的任务都早于它结束，那么当前线程就无法在第一个任务结束之前获得执行结果，但是别着急，Java 为你提供了解决方案——CompletionService。 
		一个 CompletionService 就是一个服务，用以简化等待任务的执行结果，实现的类是 ExecutorCompletionService，该类基于 ExecutorService
	 * 通过这段代码，我们可以根据执行结束的顺序获取对应的结果，而无需维护一个 Future 对象的集合。 
	 * 来源： http://825635381.iteye.com/blog/2184605
	 * 创建日期：2016年1月26日 下午3:09:29 
	 * @author malitao
	 */
	public static void call03(){
		ExecutorService pool = Executors.newCachedThreadPool();
		CompletionService<String> cs = new ExecutorCompletionService<String>(pool);
		for(int i = 1; i < 5; i++) {
			cs.submit(new CallableDetail(i));
		}
		// 可能做一些事情
		for(int i = 1; i < 5; i++) {
			long begin = System.currentTimeMillis();
			try{System.out.println(cs.take().get());} catch (Exception e) {}
			System.out.println("使用阻塞的方式 用时："+(System.currentTimeMillis()-begin));
		}
	}

//参考：
//http://tonl.iteye.com/blog/1874187
//http://wenku.baidu.com/link?url=dSpjAPWQfBLvODWbiE8H3XYy8cPUbKMKeUa3DVxTUqsvmHa0Kj9i3JGYG8Mrm1M0p1xuiyh9WvNMQcAq-vS_HV8s5oXPbfQL-6ct_0VntxC
//http://blog.csdn.net/ghsau/article/details/7451464
	
//20160126增加：主线程等待子线程各种方案比较
//http://825635381.iteye.com/blog/2184605
	
}
