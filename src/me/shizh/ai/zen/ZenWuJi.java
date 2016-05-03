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

public class ZenWuJi {

	public static Map<String, List<Category>> CATEGORIES;
	public static User USER = new User();
	
	private static void saveAll() {
		System.out.println("[INFO]Saving All ...");
		Category.saveAll(CATEGORIES);
		User.saveAll(USER);
	}

	public static void main(String[] args) {
		Matcher matcher = Pattern
				.compile(
						"(?<=不知道|我想知道|不理解|不明白|不懂|(?:解释|说明)(?一下)?)([\\w,\\d,一-龥]+)(?=是什么|的意思|(?:应该被|应该|应|[^应]该|[^应该]被)(?=如何理解|怎么理解|如何解释))")
				.matcher("不明白nasa是什么");
		if (matcher.find()) {
			System.out.println(matcher.group("str"));
		}
		System.exit(0);
		
		
		Scanner scnner = new Scanner(System.in);
//		InteractionOutput.output("tv","请输入你的用户名:");
//		USER = User.initUserIns(scnner);
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
