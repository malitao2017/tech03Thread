package tt13Exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Exchanger
 * 线程间的通讯，本例只是两个线程之间的数据交换
 * 注意：两个线程若一个没有执行exchange()方法，则会一直等待
 * @author malitao
 *
 */
public class ExchangerDemo {
	private static final Exchanger<String> exgr = new Exchanger<>();
	private static Executor executor = Executors.newFixedThreadPool(2);
	public static void main(String[] args) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String B = "银行流水B";
				String A = null;
				try {
					A = exgr.exchange(B);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("B中：  A:"+ A +" B:"+B);
			}
		});
		executor.execute(new Runnable() {
			@Override
			public void run() {
				String A = "银行流水A";
				try {
					exgr.exchange(A);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
