package com.vaskka.spiderscu;

/**
 * debug�ڲ���
 * @author czm
 *
 */
class Debug {
	static boolean DEBUG = true;
	
	/**
	 * ����̨���LOG
	 * @param message LOG����
	 */
	static void L(String message) {
		if (DEBUG) {
			System.out.println(message);
		}
	}
	
}
