package com.vaskka.spiderscu;

/**
 * debug内部类
 * @author czm
 *
 */
class Debug {
	static boolean DEBUG = true;
	
	/**
	 * 控制台输出LOG
	 * @param message LOG内容
	 */
	static void L(String message) {
		if (DEBUG) {
			System.out.println(message);
		}
	}
	
}
