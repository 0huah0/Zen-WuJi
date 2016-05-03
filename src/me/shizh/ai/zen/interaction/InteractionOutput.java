package me.shizh.ai.zen.interaction;

import me.shizh.ai.zen.features.action.Action;
import me.shizh.ai.zen.features.voice.Voice;
import me.shizh.common.util.StringUtil;

public class InteractionOutput {
	
	final static boolean t_enable = true;
	final static boolean v_enable = false;
	final static boolean a_enable = true;

	@SuppressWarnings("unused")
	public static void output(String type, String str) {
		if (StringUtil.isNull(type)) {
			type = "";
		}

		if (t_enable && type.contains("t")) {
			System.out.println(str);
		}

		if (v_enable && type.contains("v")) {
			Voice.doVoice(str);
		}

		if (a_enable && type.contains("a")) {
			Action.doAction(str);
		}
	}
}
