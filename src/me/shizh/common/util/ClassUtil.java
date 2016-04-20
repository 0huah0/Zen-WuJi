package me.shizh.common.util;

import java.lang.reflect.Method;
import java.util.List;

public class ClassUtil {
	/**
	 * for  Machine Decision
	 * @param classname
	 * @param method
	 * @param grps
	 * @return
	 */
	public static String md_invoke(String classname, String method, List<String> grps) {
		String output = null;
		try {
			Class<?> clz = Class.forName(classname);
			Object o = clz.newInstance();
			Method m = clz.getMethod(method, List.class);
			output = (String) m.invoke(o, grps);
		} catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return output;
	}
	
}
