package me.shizh.ai.zen.descion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import me.shizh.ai.zen.ZenWuJi;
import me.shizh.common.util.ClassUtil;
import me.shizh.common.util.RandomUtil;
import me.shizh.common.util.StringUtil;

public class Descion {

	/**
	 * DO DESCION
	 * @param input
	 * @return output
	 */
	public static String doDescionByStringInput(String input) {
		List<String[]> ms = ZenWuJi.USER.getInteractionVO().getMemoryStack();
		if(ms.get(ms.size()-1)[1].contains(input+"|")){
			//TODO
		}
		List<String> outputs = new ArrayList<String>();

		List<String> categories = extractCategories(input);
		for (String cate : categories) {
			List<Category> cs = ZenWuJi.CATEGORIES.get(cate);
			outputs.addAll(matchDescionInCs(cs, input));
		}
		
		if(outputs.size()==0){
			outputs.addAll(matchDescionInCs(ZenWuJi.CATEGORIES.get("default"), input)); 
		}
		
		return choiceOne(outputs); // choice one
	}

	
	/**
	 * 如果返回结果有多个需要按一定逻辑返回最适合的一个
	 * @param outputs
	 * @return
	 */
	private static String choiceOne(List<String> outputs) {
		/*System.out.print("OUTPUT:");
		for (String output : outputs) {
			System.out.println(output);
		}*/

		// TODO choiceOne
		return outputs.get(RandomUtil.random(0, outputs.size()));
	}

	
	/**
	 * 正则表达式匹配
	 * @param cs
	 * @param input
	 * @return
	 */
	private static List<String> matchDescionInCs(List<Category> cs, String input) {
		List<String> outputs = new ArrayList<String>();
		for (Category c : cs) {
			try{
				String output = null;
				// 按正则匹配
				Pattern pattern = Pattern.compile(c.get_regular());
		        Matcher matcher = pattern.matcher(input); 
		        if(matcher.find()) {
		            //output = match(c, input);
		        	output = c.getDefault_(); // 使用default
		        	
		        	List<String> grps = new ArrayList<String>();
		        	for(int i=1;i<=matcher.groupCount();i++){
		        		grps.add(matcher.group(i));
		        		if(StringUtil.isNotNull(output)){
		        			output = output.replace("{"+i+"}", matcher.group(i));
		        		}
		            }
		        	
		            if (StringUtil.isNotNull(c.getMd_())) { // 使用MD
		    			output = ClassUtil.md_invoke(c.getMd_(),input,"doMachineDecision",grps);
		    		} else {
		    			if (StringUtil.isNotNull(c.getRandom_())) {// 使用随机
		    				output = c.getRandom_().get(
		    						RandomUtil.random(0, c.getRandom_().size()));
		    			}
		    		}
		            if(StringUtil.isNotNull(output) && !"null".equals(output)){
		            	outputs.add(output);
		            }
		        }
			}catch(PatternSyntaxException e){
				System.out.println(e.getMessage());
			}
		}
		return outputs;
	}


	/**
	 * 分类到CATEGORIES的类型中，对input做问题分类
	 * 如果找不到，则返回default
	 * @param input
	 * @return
	 */
	private static List<String> extractCategories(String input) {
		List<String> ls = new ArrayList<String>();
		
		//TODO 分类到CATEGORIES的类型中
		ls.add("main");
		ls.add("complain");
		return ls;
	}
	
}
