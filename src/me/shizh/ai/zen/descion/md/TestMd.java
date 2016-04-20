package me.shizh.ai.zen.descion.md;

import java.lang.reflect.Method;
import java.util.List;

public class TestMd implements MdBase {
	public String doMachineDecision(List<String> input) {
		return "input.size() =\'" + input.size() + "'.";
	}
	
	
	public static void main(String[] args) {
		try {
			Class<?> clz = Class.forName("me.shizh.ai.zen.descion.md.TestMd");
			Object o = clz.newInstance();
			Method m = clz.getMethod("doMachineDecision", String.class);
			String output = (String) m.invoke(o, "ABC");
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
}
