package me.shizh.ai.zen;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import me.shizh.ai.zen.descion.Category;
import me.shizh.ai.zen.features.voice.Voice;
import me.shizh.ai.zen.interaction.Response;
import me.shizh.ai.zen.user.User;

public class ZenWuJi {

	public static Map<String, List<Category>> CATEGORIES;
	public static User USER = null;
	
	private static void init() {
		CATEGORIES = Category.loadCategories();
		USER = User.initUserIns();
	}
	
	private static void saveAll() {
		System.out.println("[INFO]Saving All ...");
		
		Category.saveAll(CATEGORIES);
		User.saveAll(USER);
	}

	public static void main(String[] args) {

//		Pattern pattern = Pattern.compile(".*([昨|前|明|后])天.*冷\\S*");
//        Matcher matcher = pattern.matcher("昨天好冷啊"); 
//        if(matcher.find()) {
//            System.out.println(matcher.group(1));
//        }
        
//		System.exit(0);
		
		init();
		
		Scanner scnner = new Scanner(System.in);
		System.out.print("YOUR NAME IS :");
		String name = scnner.nextLine();
		USER.setUid(name);
		
		boolean quit = false;
		while (!quit) {
			System.out.print(USER.getUid()+":");
			String input = scnner.nextLine();
			String output = Response.text_answer(Response.InputType.TEXT, input);
			System.out.println("Zen:"+ output);
			Voice.doVoice(output);
			
			if ("quit".equals(input)) {
				quit = true;
				saveAll();	//退出前持久化信息
			}
		}
		scnner.close();
	}
	
}
