package tt12Semaphore;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * 功能：每次只允许最多运行固定数目的线程
 * 理解：一条马路只允许100辆汽车行驶，本对象就相当于红绿灯，马路驾驶出了5辆车，本信号量就放5辆车就去，一直保持不多于100量的水平
 * 
 * @author malitao
 *
 */
public class SemaphoreDemo {
	static int num = 30;
	private static Executor executor = Executors.newFixedThreadPool(num);
	private static Semaphore sem = new Semaphore(10);
	public static void main(String[] args) {
		for(int i=0;i<num;i++){
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						sem.acquire();//获取运行权限
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//每次只能最多有10个线程在运行
					System.out.println("执行线程名："+Thread.currentThread().getName()+" 执行save data");
					sem.release();//释放运行权限
				}
			});
		}
	}
	
	
}
