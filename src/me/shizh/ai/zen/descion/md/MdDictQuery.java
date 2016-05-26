package me.shizh.ai.zen.descion.md;

import java.util.ArrayList;
import java.util.List;

import me.shizh.ai.zen.features.text.Text;
import me.shizh.common.util.StringUtil;

/**
 * 处理用户对机器人的设置修改命令处理 修改年龄|性别|名字
 * 
 * @author Administrator
 * 
 */
public class MdDictQuery implements MdBase {

	public String doMachineDecision(String input,List<String> inputs) {
		String output = "";
		if(inputs.size() >= 1){
			String[] attr = Text.queryWord(inputs.get(0));
//			output = attr[3];//trans_result
			if(StringUtil.isNotNull(attr[4])){
				output = attr[4];//dict_detail
			}else{
				output = attr[8];//baike_content
			}
		}else {
			output = null;
		}
		return output;
	}

	public static void main(String[] args) {
		try {
			List<String> grps = new ArrayList<String>();
			for (int i = 1; i <= 3; i++) {
				grps.add("IDX_" + i);
			}

			Class<?> clz = Class.forName("me.shizh.ai.zen.descion.md.MdDictQuery");
			String output = (String) clz.getMethod("doMachineDecision", List.class).invoke(clz.newInstance(),"", grps);
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.exit(0);
	}

}
