/**   
 * Copyright © 2016 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech03Thread
 * 描述信息: 
 * 创建日期：2016年1月19日 下午3:50:36 
 * @author malitao
 * @version 
 */
package t2way.callable.detail;
import java.util.concurrent.Callable;

public class CallableDetail implements Callable<String>{//callable有个<V>,这个V就是call函数的返回值类型  
	public CallableDetail(int ticket){
		this.ticket = ticket;
	}
	private int ticket = 10;
	
	@Override
	public String call() throws Exception {//这儿可以抛出异常，而Runnable接口的run函数不可以
		System.out.println("thread"+ticket+":睡眠");
		Thread.sleep(1000);
		return "callable"+ticket+":执行成功";//Runnable接口的run函数是没有返回值的  
	}

}
