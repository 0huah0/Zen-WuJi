package me.shizh.ai.zen.interaction;

import com.baidu.tts.BaiduTts;

import me.shizh.ai.zen.descion.Descion;
import me.shizh.ai.zen.features.voice.Voice;


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
	 * 用文字回答用户问题
	 * @param inputi
	 */
	public static String text_answer(Response.InputType inputType,String input) {
		String ouput = null;
		switch (inputType) {
		case VOICE:
			ouput = _answeVoice(input);
			break;
		case ACTION:
			ouput = _answeAction(input);
			break;
		default:
			ouput = _answeText(input);
			break;
		}
		return ouput;
	}
	
	/**
	 * 文字回答文字
	 * @param input
	 * @return
	 */
	private static String _answeText(String input) {
		return Descion.doDescionByStringInput(input);
	}
	
	/**
	 * 输入是Action
	 * @param input action sequence 输入是动作序列
	 * @return
	 */
	private static String _answeAction(String input) {
		//用文字回答动作
		
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 输入是Voice
	 * @param input voice file path 输入是音频文件路径
	 * @return
	 */
	private static String _answeVoice(String input) {
		input = BaiduTts.voice2text(input);
		return  _answeText(input);
	}

	/**
	 * 语音回答用户问题
	 * @param input
	 */
	public static String voice_answer(Response.InputType inputType,String input) {
		String output = text_answer(inputType,input);
		Voice.doVoice(output);//返回语音对应文本
		return output;
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
