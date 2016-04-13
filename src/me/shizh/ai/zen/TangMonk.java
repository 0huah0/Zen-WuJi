package me.shizh.ai.zen;

import java.util.Map;
import java.util.Scanner;

import me.shizh.ai.zen.descion.Categories;
import me.shizh.ai.zen.interaction.Response;
import me.shizh.ai.zen.user.User;

public class TangMonk {

	private static Map<String,Categories> CATEGORIES;
	
	public static void main(String[] args) {
		System.out.println("");
		Scanner scnner = new Scanner(System.in);
		
		System.out.println("Your name:");
		String name = scnner.nextLine();
		User user = User.initUserIns(name);
		
		boolean quit = false;
		while(!quit){
			
			String input = scnner.nextLine();
			System.out.println("log:"+input);
			System.out.println("Tank:"+Response.text_answer(Response.InputType.TEXT,input));
			if("quit".equals(input)){
				quit = true;
			}
		}
		
	}

}