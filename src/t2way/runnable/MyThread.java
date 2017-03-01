package t2way.runnable;

public class MyThread implements Runnable{
	private int ticket = 10;
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			if(ticket>0)
				System.out.println("thread的票数为："+ticket--);
		}
	}
}
