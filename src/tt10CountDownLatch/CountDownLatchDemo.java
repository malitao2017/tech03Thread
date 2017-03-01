package tt10CountDownLatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
/**
 * 在此之前有一个实现线程顺序执行的方法 .join()
 * CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 二读：20170228龙抬头 本例在等待和阻塞的是main函数，其他的跑的两个线程是没有先后关系的，指的是主线程等待他们都操作完，CountDownLatch最后的数目变成0 的时候才可以往下进行
 * 最好应用场景是解析一个多sheet页的excel文档，可以写多线程来执行sheet，最后完毕之后主线程进行汇总
主要方法
 public CountDownLatch(int count);
 public void countDown();
 public void await() throws InterruptedException
构造方法参数指定了计数的次数
countDown方法，当前线程调用此方法，则计数减一
awaint方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
latch.countDown(); 建议放在 finally里执行
 * @author malitao
 *
 */
public class CountDownLatchDemo {
	final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) throws InterruptedException {
    	CountDownLatch latch=new CountDownLatch(2);//两个工人的协作
    	Worker worker1=new Worker("zhang san", 5000, latch);
    	Worker worker2=new Worker("li si", 8000, latch);
    	worker1.start();//
    	worker2.start();//
    	latch.await();//等待所有工人完成工作
        System.out.println("运行的两个线程都晚了，主线程可以运行了。all work done at "+sdf.format(new Date()));
	}
    
    
    static class Worker extends Thread{
    	String workerName; 
    	int workTime;
    	CountDownLatch latch;
    	public Worker(String workerName ,int workTime ,CountDownLatch latch){
    		 this.workerName=workerName;
    		 this.workTime=workTime;
    		 this.latch=latch;
    	}
    	public void run(){
    		System.out.println("Worker "+workerName+" do work begin at "+sdf.format(new Date()));
    		doWork();//工作了
    		System.out.println("Worker "+workerName+" do work complete at "+sdf.format(new Date()));
    		latch.countDown();//工人完成工作，计数器减一

    	}
    	
    	private void doWork(){
    		try {
				Thread.sleep(workTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
    
     
}

