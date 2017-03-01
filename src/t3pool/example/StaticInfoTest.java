/**   
 * Copyright © 2015 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech03Thread
 * 描述信息: 
 * 创建日期：2015年12月17日 下午3:07:03 
 * @author malitao
 * @version 
 */
package t3pool.example;

import java.util.concurrent.Executors;

import t3pool.example1.PoolBean;

/** 
 *  静态变量的外部调用呈现
 * 创建日期：2015年12月17日 下午3:07:03 
 * @author malitao
 */
public class StaticInfoTest {
	public static void main(String[] args) {
//		fun1();
		fun();
	}
	//  线程类变量
	@SuppressWarnings("static-access")
	public static void fun(){
		//线程类变量,只要是执行过方法，或执行其中的静态都会运行
		//跟对象无关
//	    PoolBean.fun();
	    PoolBean bean1 = new PoolBean();
	    PoolBean bean2 = new PoolBean();
	    System.err.println("o:"+PoolBean.executor);
	    System.out.println("1:"+bean1.executor);
	    System.out.println("2:"+bean2.executor);
	    PoolBean.executor = null;
	    System.err.println("PoolBean change 0o:"+PoolBean.executor);
	    System.out.println("01:"+bean1.executor);
	    System.out.println("02:"+bean2.executor);
	    bean1.executor = Executors.newSingleThreadExecutor();
	    System.err.println("bean1 change 1o:"+PoolBean.executor);
	    System.out.println("11:"+bean1.executor);
	    System.out.println("12:"+bean2.executor);
	    bean2.executor = Executors.newSingleThreadScheduledExecutor();
	    System.err.println("bean2 change 2o:"+PoolBean.executor);
	    System.out.println("21:"+bean1.executor);
	    System.out.println("22:"+bean2.executor);
	}
	//  常见的 类型，跟对象没关系
	@SuppressWarnings("static-access")
	public static void fun1(){
		//string的情况
	    StaticInfo info1 = new StaticInfo();
	    StaticInfo info2 = new StaticInfo();
	    System.out.println("o:"+StaticInfo.str);
	    System.out.println("1:"+info1.str);
	    System.out.println("2:"+info1.str);
		StaticInfo.str = "o change";
	    System.out.println("o:"+StaticInfo.str);
	    System.out.println("1:"+info1.str);
	    System.out.println("2:"+info1.str);
	    info1.str = "info1 change";
	    System.out.println("o:"+StaticInfo.str);
	    System.out.println("1:"+info1.str);
	    System.out.println("2:"+info1.str);
	    info2.str = "info2 change";
	    System.out.println("o:"+StaticInfo.str);
	    System.out.println("1:"+info1.str);
	    System.out.println("2:"+info1.str);
	}
}
