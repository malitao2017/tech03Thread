package t8currentThread;

/**
 * Thread.currentThread()获取当前线程。
 * this 则不行
 * 
 * 著作权归作者所有。
商业转载请联系作者获得授权，非商业转载请注明出处。
作者：于光荣
链接：https://www.zhihu.com/question/20944522/answer/28119453
来源：知乎

看上面，貌似你会用Thread.currentThread()。但是问题来了，为毛不直接用对象操作就行了啊?把run()方法里的Thread.currentThread() 直接替换成 this不就行了(替换成注释掉的代码)，直接用对象多省事啊。不知你们学习时有没有过这样的疑惑，因为我确实想不到哪里需要调用到这个线程而拿不到这个线程的情况。可是新的问题又出现了，你试试看运行上面这段代码，this和Thread.currentThread()输出的内容是否相同？运行比较一下吧。没想到吧，差别明显啊！所以题外插一句：如果调用isInterrupted返回true,this就是当前线程对象，此时Thread.currentThread()与this表示同一对象。否则，就必须使用Thread.currentThread()获取当前线程。而且既然是使用到了多线程，多半情况下都不会知道系统当前执行的是哪块线程，所以你需要调用Thread.currentThread()方法来获取系统当前正在执行的一条线程，然后才可以对这个线程进行其他操作，就是这个意思。
 * @author malitao
 *
 */
class MyThread extends Thread {

	@Override
	public void run() {
		try {
			Thread.sleep(500);
			Thread t = Thread.currentThread();
			System.out.println("当前线程名字：" + t.getName() + " 当前线程的优先级别为："+ t.getPriority() + " ID:" + t.getId());
//			System.out.println("当前线程名字：" + this.getName() + " 当前线程的优先级别为：" + this.getPriority() + " ID:"+ this.getId());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class TestDemo {
	public static void main(String[] args) {
		MyThread mt = new MyThread();
		new Thread(mt, "Name1").start();
		new Thread(mt, "Name2").start();
		new Thread(mt).start();
		/**
		 * 这个就是调用了 new Thread()的默认构造方法
		 */
		mt.start(); 
		System.out.println(Thread.currentThread().getName()); // main主方法
	}
}
