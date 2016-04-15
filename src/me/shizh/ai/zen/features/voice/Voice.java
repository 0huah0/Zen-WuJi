package me.shizh.ai.zen.features.voice;

import java.util.HashMap;

import me.shizh.ai.zen.ZenWuJi;

import com.baidu.tts.BaiduTts;

/**
 * 语言能力：语音转文字，文字转语音
 * 
 * @author Administrator
 * 
 */
public class Voice {

	// 根据人不同年龄的声音特征为其设定声音参数（语速，音调，音量）
	// 音调：声调高低，低音、中音、高音。年龄越大，音调趋于低沉。男孩子比较低的， 女孩子音调比较高的。“清脆悦耳”的声音叫做音调高。
	// 年龄-音调：年龄越大，音调越低
	// 情绪-音调 :越激动，音调就越高
	// 性格-音调 :性格外向的人的情绪可能会比内向的人更容易进入提高自己音调的情绪状态.外性格开朗的人音量大
	// http://www.zhihu.com/question/19709842
	public static void doVoice(String input) {
		/*
		 * spd 选填 语速，取值 0-9，默认为 5 pit 选填 音调，取值 0-9，默认为 5 vol 选填 音量，取值 0-9，默认为 5
		 * per 选填 发音人选择，取值 0-1 ；0 为女声，1 为男声，默认为女声
		 */
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("cuid", ZenWuJi.USER.getUid());
		map.put("lan", "zh"); // 是中式发言吗
		map.put("spd", decideSpd());
		map.put("pit", decidePit());
		map.put("vol", decideVol());
		map.put("per", "女".equals(ZenWuJi.USER.getSex()) ? "0" : "1");

		BaiduTts.text2voice(input, map);
	}

	private static String decideVol() {
		int age = Integer.parseInt(ZenWuJi.USER.getAge());
		int vol = 5;
		
		//年龄影响
		if(age<13){
			vol += 2;
		}else if(age>13 && age<30){
			vol += 1;
		}else if(age>30 && age<60){
			vol += -1;
		}else{
			vol += -2;
		}
		
		//情绪影响
		String [][] emotion_vol = new String[][]{
				{"快乐","1"},{"悲伤","0"},{"恐惧","-2"},{"愤怒","4"},
				{"焦虑","0"},{"惊讶","2"},{"尴尬","-1"},{"羞耻","-1"},
				{"内疚","0"},{"鄙夷","0"},{"厌恶","0"},{"狂喜","4"},
				{"绝望","-2"},{"暴怒","4"},{"憎恶","0"},{"欲望","0"},
				{"悲哀","0"},{"害羞","0"},{"骄傲","1"},{"自信","2"},
				{"惊喜","1"},{"犹豫","0"},{"忧愁","0"},{"忧郁","-2"},{"激动","3"}};
		for (String []ev : emotion_vol) {
			if(ZenWuJi.USER.getEmotion().equals(ev[0])){
				vol += Integer.parseInt(ev[1]);
			}
		}
		
		return ""+(vol<1?1:vol);
	}

	private static String decidePit() {
		int age = Integer.parseInt(ZenWuJi.USER.getAge());
		int vol = 5;
		
		//年龄影响
		if(age<13){
			vol += 2;
		}else if(age<60){
			vol += -2;
		}
		
		//情绪影响
		String [][] emotion_ = new String[][]{
				{"快乐","1"},{"悲伤","-2"},{"恐惧","-2"},{"愤怒","2"},
				{"焦虑","0"},{"惊讶","3"},{"尴尬","-1"},{"羞耻","-1"},
				{"内疚","-1"},{"鄙夷","0"},{"厌恶","1"},{"狂喜","2"},
				{"绝望","-2"},{"暴怒","3"},{"憎恶","0"},{"欲望","0"},
				{"悲哀","-2"},{"害羞","-1"},{"骄傲","1"},{"自信","1"},
				{"惊喜","1"},{"犹豫","0"},{"忧愁","-1"},{"忧郁","-2"},{"激动","2"}};
		for (String []ev : emotion_) {
			if(ZenWuJi.USER.getEmotion().equals(ev[0])){
				vol += Integer.parseInt(ev[1]);
			}
		}
		
		return ""+(vol<1?1:vol);
	}

	private static String decideSpd() {
		int age = Integer.parseInt(ZenWuJi.USER.getAge());
		int vol = 5;
		
		//年龄影响
		if(age<13){
			vol += 1;
		}else if(age<60){
			vol += -2;
		}
		
		//情绪影响
		String [][] emotion_ = new String[][]{
				{"快乐","1"},{"悲伤","0"},{"恐惧","2"},{"愤怒","0"},
				{"焦虑","0"},{"惊讶","-2"},{"尴尬","2"},{"羞耻","0"},
				{"内疚","-1"},{"鄙夷","0"},{"厌恶","3"},{"狂喜","1"},
				{"绝望","-3"},{"暴怒","3"},{"憎恶","3"},{"欲望","0"},
				{"悲哀","-2"},{"害羞","-2"},{"骄傲","1"},{"自信","0"},
				{"惊喜","1"},{"犹豫","-3"},{"忧愁","-1"},{"忧郁","-2"},{"激动","3"}};
		for (String []ev : emotion_) {
			if(ZenWuJi.USER.getEmotion().equals(ev[0])){
				vol += Integer.parseInt(ev[1]);
			}
		}
		
		return ""+(vol<1?1:vol);
	}
}
