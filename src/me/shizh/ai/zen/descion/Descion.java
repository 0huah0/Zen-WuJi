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

	public static String doDescionByStringInput(String input) {
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

	private static String choiceOne(List<String> outputs) {
		/*System.out.print("OUTPUT:");
		for (String output : outputs) {
			System.out.println(output);
		}*/

		// TODO choiceOne
		return outputs.get(RandomUtil.random(0, outputs.size()));
	}

	/**
	 * 在
	 * 
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
		            output = match(c, input);
		            for(int i=1;i<=matcher.groupCount();i++){
		            	output = output.replace("{"+i+"}", matcher.group(i));
		            }
		            outputs.add(output);
		        }
			}catch(PatternSyntaxException e){
				System.out.println(e.getMessage());
			}
		}
		return outputs;
	}

	private static String match(Category c, String input) {
		String output = null;

		if (StringUtil.isNotNull(c.getMd_())) { // 使用MD
			output = ClassUtil.md_invoke(c.getMd_(), "doMachineDecision",
					input);
		} else {
			if (StringUtil.isNotNull(c.getRandom_())) {// 使用随机
				output = c.getRandom_().get(
						RandomUtil.random(0, c.getRandom_().size()));
			}
		}

		if (StringUtil.isNull(output)) { // 使用default
			output = c.getDefault_();
		}

		return output;
	}


	/**
	 * 分类到CATEGORIES的类型中
	 * 如果找不到，则返回default
	 * @param input
	 * @return
	 */
	private static List<String> extractCategories(String input) {
		List<String> ls = new ArrayList<String>();
		
		//TODO 分类到CATEGORIES的类型中
		ls.add("complain");
		return ls;
	}
	
}
