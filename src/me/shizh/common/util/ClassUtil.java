package me.shizh.common.util;

import java.lang.reflect.Method;

public class ClassUtil {
	/**
	 * for  Machine Decision
	 * @param classname
	 * @param method
	 * @param input
	 * @return
	 */
	public static String md_invoke(String classname, String method, String input) {
		String output = null;
		try {
			Class<?> clz = Class.forName(classname);
			Object o = clz.newInstance();
			Method m = clz.getMethod(method, String.class);
			output = (String) m.invoke(o, input);
		} catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return output;
	}
	
}
