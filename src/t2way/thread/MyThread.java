package t2way.thread;

public class MyThread extends Thread{
	private int ticket = 10;
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			if(ticket>0)
				System.out.println("runnable的票数为："+ticket--);
		}
	}
}
