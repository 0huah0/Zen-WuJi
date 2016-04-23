package me.shizh.ai.zen;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import me.shizh.ai.zen.descion.Category;
import me.shizh.ai.zen.features.voice.Voice;
import me.shizh.ai.zen.interaction.InteractionOutput;
import me.shizh.ai.zen.interaction.Response;
import me.shizh.ai.zen.user.User;

public class ZenWuJi {

	public static Map<String, List<Category>> CATEGORIES;
	public static User USER = new User();
	
	private static void saveAll() {
		System.out.println("[INFO]Saving All ...");
		Category.saveAll(CATEGORIES);
		User.saveAll(USER);
	}

	public static void main(String[] args) {
//        Matcher matcher = Pattern.compile("(<num>\\d)+(<str>\\w)+")
//        		.matcher("12312adasd"); 
//        if(matcher.find()) {
//        	System.out.println(matcher.group("str"));
//        }
//		System.exit(0);
		
		
		Scanner scnner = new Scanner(System.in);
		//InteractionOutput.output("tv","请输入你的用户名:");
		USER = User.initUserIns(scnner);
		InteractionOutput.output("vt","系统提示：正在启动,请稍等...");
		CATEGORIES = Category.loadCategories();
		
		InteractionOutput.output("tv","你好！系统已启动，欢迎使用。");
		
		boolean quit = false;
		while (!quit) {
			System.out.print(USER.getName()+":");
			String input = scnner.nextLine();
			if ("quit".equals(input)) {
				quit = true;
				saveAll();	//退出前持久化信息
			}
			
			String output = Response.text_answer(Response.InputType.TEXT, input);
			String zen_name = USER.getExt_attr().get("SETTING").get("name");
			System.out.println(zen_name +":"+ output);
			Voice.doVoice(output);
			
		}
		scnner.close();
	}
	
}
