package me.shizh.ai.zen.descion.md;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.shizh.common.util.StringUtil;

/**
 * 处理数学计算
 * TODO 还不支持小数
 * @author Administrator
 */
public class MdMathSimple implements MdBase {
	
	public String doMachineDecision(List<String> inputs) {
		String output="是数学计算吗？我不能识别你的表达，请说得更清楚一点。";
		
		Long r = 0L;
		if(inputs.size() >= 2){
			Long a = 0L,b = 0L;
			String flag = inputs.get(1);
			
			a = parse(inputs.get(0));
			b = parse(inputs.get(2));
			
			if(flag.matches("[加+]")){
				r = a + b;
			}else if(flag.matches("[减-]")){
				r = a - b;
			}else if(flag.matches("[乘Xx×*]")){
				r = a * b;
			}else if(flag.matches("[除/÷]")){
				r = a / b;
			}else if(flag.matches("[模%]")){
				r = a % b;
			}
		}
			
		return r==0?output:r.toString();
	}
	
	
	private Long parse(String input) {
		Long a = 0L;
		if(input.matches("\\d+")){
			a = Long.parseLong(input);
		}else if(input.matches("[一二三四五六七八九零〇十百千万亿]+")){
			a = StringUtil.chineseToNum(input);
		}else if(input.matches("[^十百千万亿]+")){
			a = Long.parseLong(StringUtil.chineseNumFormat(input));
		}else{
			a = StringUtil.chineseToNum(StringUtil.numChineseFormat(input));
		}
		return a;
	}


	public static void main(String[] args) {
		
		try {
			Class<?> clz = Class.forName("me.shizh.ai.zen.descion.md.MdMathSimple");
			Object o = clz.newInstance();
			Method m = clz.getMethod("doMachineDecision",List.class);
			
			List<String> grps = new ArrayList<String>();
        	
			grps.add("1二3");
			grps.add("加");
			grps.add("七千4百〇1十");
			
			String output = (String) m.invoke(o,grps );
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
}
