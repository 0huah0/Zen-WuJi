package me.shizh.ai.zen.descion.md;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.shizh.ai.zen.ZenWuJi;
import me.shizh.ai.zen.user.User;

/**
 * 处理用户对机器人的设置修改命令处理
 * 修改年龄|性别|名字
 * @author Administrator
 *
 */
public class ChangeSettingMd implements MdBase {
	
	public String doMachineDecision(List<String> inputs) {
		String output = "已修改。";
		
		if(inputs.size() >= 2){
			Map<String, String> setting = ZenWuJi.USER.getExt_attr().get("SETTING");
			if("年龄".equals(inputs.get(0))){
				setting.put("age", User.parseAge(inputs.get(1).replace("岁", "")));
			}else 	if ("性别".equals(inputs.get(0))) {
				setting.put("sex", inputs.get(1).equals("男")?"男":"女");
			}else if ("名字".equals(inputs.get(0))) {
				setting.put("name", inputs.get(1));
			}else{
				output = null;
			}
		}else {
			output = null;
		}
		
		return output;
	}
	
	
	public static void main(String[] args) {
		try {
			Class<?> clz = Class.forName("me.shizh.ai.zen.descion.md.ChangeSettingMd");
			Object o = clz.newInstance();
			Method m = clz.getMethod("doMachineDecision",List.class);
			
			List<String> grps = new ArrayList<String>();
        	for(int i=1;i<=3;i++){
        		grps.add("IDX_"+i);
            }
			
			String output = (String) m.invoke(o,grps );
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
}
