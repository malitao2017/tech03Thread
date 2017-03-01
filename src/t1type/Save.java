/*
 * save.java
 * Copyright: TsingSoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package t1type;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author LT
 * @version 1.0, 2015年9月27日
 */
public class Save {
	Map<String,String> map = new HashMap<String,String>();
	Map<String,String> map2= new ConcurrentHashMap<String,String>(); 
//	volatile 
}
