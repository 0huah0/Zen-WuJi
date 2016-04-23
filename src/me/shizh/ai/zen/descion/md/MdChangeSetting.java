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
public class MdChangeSetting implements MdBase {
	
	public String doMachineDecision(List<String> inputs) {
		String output = "已修改。";
		
		if(inputs.size() >= 3){
			Map<String, String> setting = ZenWuJi.USER.getExt_attr().get("SETTING");
			if("年龄".equals(inputs.get(1))){
				String age = User.parseAge(inputs.get(1).replace("岁", ""));
				if("你的".equals(inputs.get(0))){
					setting.put("age", age);
				}else if("我的".equals(inputs.get(0))){
					ZenWuJi.USER.setAge(age);
				}else{
					output = "我无法修改"+inputs.get(0)+"的"+inputs.get(1);
				}
			}else 	if ("性别".equals(inputs.get(1))) {
				String sex = inputs.get(2).equals("男")?"男":"女";
				if("你的".equals(inputs.get(0))){
					setting.put("sex",sex);
				}else if("我的".equals(inputs.get(0))){
					ZenWuJi.USER.setSex(sex);
				}else{
					output = "我无法修改"+inputs.get(0)+"的"+inputs.get(1);
				}
				
			}else if ("名".equals(inputs.get(1))) {
				if("你的".equals(inputs.get(0))){
					setting.put("name", inputs.get(2));
				}else if("我的".equals(inputs.get(0))){
					ZenWuJi.USER.setName(inputs.get(2));
				}else{
					output = "我无法修改"+inputs.get(0)+"的"+inputs.get(1);
				}
			}else{
				output = "我无法修改"+inputs.get(0)+"的"+inputs.get(1);
			}
		}else {
			output = "是要修改配置吗？请告诉我修改谁的什么为多少，比如'修改我的年龄为12岁。'";
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
