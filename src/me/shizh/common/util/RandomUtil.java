package me.shizh.common.util;

import java.util.Random;

/**
 * 
 * @ClassName: RandomUtils
 * 
 */
public class RandomUtil {
	/**
	 * 
	 * @Title: getAge
	 * @Description:产生[min,max]之间的随机整数
	 * @return int
	 * 
	 */
	public static int random(int min, int max) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
	 * 
	 * @Title: random
	 * @Description: 以相同的概率获取两个对象
	 * @param object1
	 * @param object2
	 * @return Object
	 * 
	 */
	public static Object random(Object object1, Object object2) {
		return Math.random() > 0.5 ? object1 : object2;
	}
	
	
	public static String random_username() {
		//TODO
		return "RU"+random(0,10000);
	}

}