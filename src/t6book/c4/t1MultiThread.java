package t6book.c4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class t1MultiThread {
	
	public static void main(String[] args) {
		//获取java线程管理mxbean
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		//不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
		ThreadInfo[] threadinfos = threadMXBean.dumpAllThreads(false, false);
		//遍历线程信息，仅打印线程id和线程名称信息
		for(ThreadInfo info : threadinfos){
			System.out.println("["+info.getThreadId()+"]"+info.getThreadName());
		}
	}
}
