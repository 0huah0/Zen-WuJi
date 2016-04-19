package me.shizh.ai.zen;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.shizh.ai.zen.descion.Category;
import me.shizh.ai.zen.features.voice.Voice;
import me.shizh.ai.zen.interaction.InteractionOutput;
import me.shizh.ai.zen.interaction.Response;
import me.shizh.ai.zen.user.User;
import me.shizh.common.util.StringUtil;

public class ZenWuJi {

	public static Map<String, List<Category>> CATEGORIES;
	public static User USER = null;
	
	private static void saveAll() {
		System.out.println("[INFO]Saving All ...");
		Category.saveAll(CATEGORIES);
		User.saveAll(USER);
	}

	public static void main(String[] args) {
		//http://tool.oschina.net/regex/
		StringUtil.chineseToNum("");
		System.exit(0);
		
		Scanner scnner = new Scanner(System.in);
		InteractionOutput.output("tv","请输入你的用户名:");
		USER = User.initUserIns(scnner);
		InteractionOutput.output("vt","正在启动,请稍等...");
		CATEGORIES = Category.loadCategories();
		
		InteractionOutput.output("tv","你好！系统已启动，欢迎使用。");
		
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
