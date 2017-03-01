package tt11CyclicBarrier;

import java.util.Map.Entry;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * 模拟使用可循环屏障器的应用
 * 场景： 统计每个sheet的流水结果值
 * 注意：会自动调用本线程，其他的线程都执行完了await()，才执行本线程
 * 
 * @author malitao
 */
public class CyclicBarrierDemo2Apply implements Runnable {
	//应用：分别统计sheet，最后主调线程进行汇总
	/**
	 * 注意：这里是this，其他的线程都执行完了await()，才执行本线程
	 */
	private CyclicBarrier cb = new CyclicBarrier(4,this);
	//统计每个sheet的银流结果
	private Executor executor = Executors.newFixedThreadPool(4);
	//保存每个sheet的银流结果
	private ConcurrentHashMap<String, Integer> sheetCount = new ConcurrentHashMap<String,Integer>();
	
	private void count(){
		for(int i=0;i<4;i++){
			executor.execute(new Runnable() {
				public void run() {
					sheetCount.put(Thread.currentThread().getName(), 1);
					System.out.println(Thread.currentThread().getName()+" 放入："+1);
					//计算完成，插入一个屏障
					try {
						cb.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	@Override
	public void run() {
		int result = 0;
		//汇总结果
		for(Entry<String, Integer> sheet : sheetCount.entrySet()){
			result += sheet.getValue();
		}
		System.out.println("sheet都执行完毕，统计结果："+result);
		sheetCount.put("result", result);
	}
	
	public static void main(String[] args) {
		CyclicBarrierDemo2Apply dome = new CyclicBarrierDemo2Apply();
		dome.count();
	}
}




