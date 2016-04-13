package me.shizh.ai.tangmonk.interaction;


/**
 * 响应用户请求
 * @param input
 */
public class Response {

	public enum InputType {  
	  TEXT, 	//文字
	  VOICE, 	//声音
	  ACTION	//动作：表情、眼神、肢体语言
	} 


	/**
	 * 文字回答用户问题
	 * @param inputi
	 */
	public static String text_answer(Response.InputType inputType,String input) {
		return input;
		
		
	}
	
	/**
	 * 语音回答用户问题
	 * @param input
	 */
	public static String voice_answer(Response.InputType inputType,String input) {
		//TODO 语音回答用户问题
		String output = text_answer(inputType,input);
		
		return output;//返回语音对应文本
	}
	
	/**
	 * 动作响应用户
	 * @param input
	 */
	public static String action_answer(Response.InputType inputType,String input) {
		//TODO 肢体语言，表情
		
		return input;
	}
	
	
}
