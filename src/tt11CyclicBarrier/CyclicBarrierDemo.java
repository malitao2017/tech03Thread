package tt11CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	//在此之前实现线程顺序执行的方式 第一：join() 第二：CountDownLatch
	/**
	 * 这里介绍第三种 可循环屏障器 CyclicBarrier 
	 * 同 CountDownLatch的不同
	 * 第一： 使用 cb.await() 和 cdl.countDown()
	 * 第二： cb可以使用reset()重启
	 * 第三： cb可以使用getNumberWaiting()获得阻塞线程数量
	 * 第四： cb可以使用isBroken()获得阻塞线程是否被中断
	 * 
	 * 使用方法：1.定义数目 2.每个线程都执行了cb.await()之后主调线程可往下运行
	 * 
	 * @throws BrokenBarrierException 
	 * @throws InterruptedException 
	 * 
	 */
	public static void main(String[] args) throws Exception {
		//使用方法一：一般情况，等待所有执行完，主调线程可往下运行
		/**
		 * 注意：使用的时候，定义的数目是包含主调线程数的，如调用两个线程，那么就应该定义3，而countDownLatch定义为2
		 */
//		use1base();
		
		//使用方式二：高级配置，可以设置一个最后执行的一个对象，其他的线程一旦执行await()就会执行此线程
		/**
		 * 注意：高级方法会自动调用该线程
		 * 常用于优先级最低的写法，参见场景应用:CyclicBarrierDemo2Apply
		 */
		use2advanced() ;
		
		//应用：分别统计sheet，最后主调线程进行汇总
		
	}
	//使用方法一：一般情况，等待所有执行完，主调线程可往下运行
	public static void use1base() throws Exception {
		CyclicBarrier cb = new CyclicBarrier(3);//这里注意和CountDownLatch不一样
		Thread cbt = new Thread(new CbThread(cb),"cbt1");
		Thread cbt2 = new Thread(new CbThread(cb),"cbt2");
		cbt.start();
		cbt2.start();
		cb.await();//注意这里的主调线程也要执行一下
		System.out.println("主调线程结束了，主调线程参与的话，所有的三个线程就是会随机分配先后");
	}
	
	//使用方式二：高级配置，可以设置一个最优先执行的对象
	public static void use2advanced() throws Exception{
		CyclicBarrier cb = new CyclicBarrier(3,new Runnable(){
			@Override
			public void run() {
				System.out.println("此种写法，优先级最高，其他的线程包括主调线程都参与了cb屏障，先执行await()，后执行逻辑；"
						+ "反之await()都在最后,那么此方法就是优先级最低，也就是说所有的线程都执行了await()，本线程才执行；"
						+ "常用于优先级最低的写法，参见场景应用:CyclicBarrierDemo2Apply");
			}
		});
		Thread cbt = new Thread(new CbThread(cb),"cbt1");
		Thread cbt2 = new Thread(new CbThread(cb),"cbt2");
		cbt.start();
		cbt2.start();
		cb.await();//注意这里的主调线程也要执行一下
		System.out.println("主调线程结束了");
	}
	
}

//基础调用屏障线程
class CbThread implements Runnable{
	private CyclicBarrier cb;
	public CbThread(CyclicBarrier c) {
		this.cb = c;
	}
	@Override
	public void run() {
		try {
			cb.await();
			System.out.println(Thread.currentThread().getName()+" ： 此处在cb.await()之前");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}




