package t0temp;


public class Test {
	public static void main(String[] args)  {
//		while(1){}
//		int u = Runtime.getRuntime().availableProcessors();
//		System.out.println(u);
//		String K =null;
//		String V = null;
//		String key =null;
//		String value = null;
////		synchronized{
//			Hashtable<String, String> table = new Hashtable<>();
//			table.put(K, V);
////		}
//		table.get(K);
//		
//		ConcurrentHashMap<Object, Object> con = new ConcurrentHashMap<>();
//		con.get(key);
//		con.put(key, value);
//		con.size();
//		ConcurrentLinkedQueue<String> a = new ConcurrentLinkedQueue<>();
//		a.add("s");
//		a.size();
//		ArrayBlockingQueue<String> b = new ArrayBlockingQueue<>(2);
		
//		int i = 4;
//		int j = i<<16;
//		System.out.println(j);
//		testAssert();
		
//		final String a = "aaa";
//		System.out.println(a);
//		
//		AA aa = new AA();
//		try {
//			aa.wait();
//			aa.notify();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		try {
//			Thread.sleep(1000);
//			Thread.yield();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	 public static void testAssert() {
	        //断言1结果为true，则继续往下执行
	        assert true;
	        System.out.println("断言1没有问题，Go！");
	 
	        System.out.println("\n-----------------\n");
	 
	        //断言2结果为false,程序终止
	        assert false : "断言失败，此表达式的信息将会在抛出异常的时候输出！";
	        System.out.println("断言2没有问题，Go！");
	    }
	 
	
}
class AA{}