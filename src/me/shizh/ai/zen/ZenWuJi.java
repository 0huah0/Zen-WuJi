package me.shizh.ai.zen;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import me.shizh.ai.zen.descion.Category;
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

		init();
		
		Scanner scnner = new Scanner(System.in);
		System.out.print("YOUR NAME IS :");
		String name = scnner.nextLine();
		USER.setUid(name);
		
		boolean quit = false;
		while (!quit) {
			String input = scnner.nextLine();
			System.out.println(USER.getUid()+":" + input);
			System.out.println("Zen:"+ Response.text_answer(Response.InputType.TEXT, input));
			if ("quit".equals(input)) {
				quit = true;
				saveAll();	//退出前持久化信息
			}
		}
		scnner.close();
	}
	
}
