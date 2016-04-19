package me.shizh.ai.zen.interaction;

import me.shizh.ai.zen.features.action.Action;
import me.shizh.ai.zen.features.voice.Voice;
import me.shizh.common.util.StringUtil;


public class InteractionOutput {
	
	
	public static void output(String type,String str){
		if(StringUtil.isNull(type)){
			type = "";
		}
		
		if(type.contains("t")){
			System.out.println(str);
		}
		
		if(type.contains("v")){
			Voice.doVoice(str);
		}
		
		if(type.contains("a")){
			Action.doAction(str);
		}
	}
}
