package me.shizh.common.util;

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
			output = (String) clz.getMethod(method, List.class).invoke(clz.newInstance(), grps);
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
}
