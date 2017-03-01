package t2way;

import java.util.concurrent.FutureTask;
import t2way.thread.MyThread;

public class ThreadTest {

	// 三种方式,对于公共资源的访问
	// 功能：共有10张票多线程卖出去
	public static void main(String[] args) {
		way1Thread();
//		way2Runnable();
//		way3Callable();
	}

	// 1.继承 Thread ，不能实现对公共资源的访问，并且不能继承其他的类，实际卖出的是20张
	public static void way1Thread() {
		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		t1.start();
		t2.start();
	}

	// 2.实现Runnable接口，可以实现对公共资源的访问，卖出的就是实际的票数
	public static void way2Runnable() {
		t2way.runnable.MyThread r1 = new t2way.runnable.MyThread();
		Thread rr1 = new Thread(r1);
		Thread rr2 = new Thread(r1);
		rr1.start();
		rr2.start();
	}
	
	// 3.Callable实现方式
	public static void way3Callable(){
		t2way.callable.MyThread c1 = new t2way.callable.MyThread();
		//FutureTask<V>是一个包装器，它通过接受Callable<String>来创建，它同时实现了Future和Runnable接口  
		FutureTask<String> ft = new FutureTask<>(c1);
		Thread cc1 = new Thread(ft);
		Thread cc2 = new Thread(ft);
		cc1.start();
		cc2.start();
		
		while(!ft.isDone()){
			System.out.println("检查线程执行完成了吗？");
			try{Thread.sleep(1);}catch (InterruptedException e) {e.printStackTrace();}
		}
		try{System.out.println(ft.get());} catch (Exception e){}
	}
}
