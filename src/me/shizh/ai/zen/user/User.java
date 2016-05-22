package me.shizh.ai.zen.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.shizh.ai.zen.interaction.InteractionOutput;
import me.shizh.ai.zen.interaction.InteractionVO;
import me.shizh.common.util.StringUtil;
import me.shizh.common.util.UuidUtil;

public class User {
	private String uid;
	private String name;// name
	private String sex;
	private int age = 28;
	private String emotion = "快乐";// 快乐、悲伤、恐惧、愤怒、焦虑、惊讶、尴尬、羞耻、内疚、鄙夷、厌恶、狂喜、绝望、暴怒、憎恶、欲望、悲哀、害羞、骄傲、自信、惊喜、犹豫、忧愁、忧郁、激动
	private UserType userType;
	private Map<String, Map<String, String>> ext_attr = new HashMap<>(); // 用户扩展属性
	private InteractionVO interactionVO = new InteractionVO();

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = Integer.parseInt(age);
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Map<String, Map<String, String>> getExt_attr() {
		if(ext_attr.get("SETTING")==null){
			ext_attr.put("SETTING", new HashMap<String, String>());
		}
		return ext_attr;
	}

	public void setExt_attr(Map<String, Map<String, String>> ext_attr) {
		this.ext_attr = ext_attr;
	}


	public InteractionVO getInteractionVO() {
		return interactionVO;
	}

	public void setInteractionVO(InteractionVO interactionVO) {
		this.interactionVO = interactionVO;
	}

	/**
	 * 返回uid为name的User对象实例 先从DB查找历史记录，若存在，从DB加载，不存在创建新用户.
	 * 
	 * @param scnner
	 * @return
	 */
	public static User initUserIns(Scanner scnner) {
		String str = scnner.nextLine();
		User u = loadUser(str);
		if(u == null){
			u = new User();
			u.setUid(UuidUtil.get32UUID());
			u.setName(str);
			
			Map<String, String> setting = new HashMap<String, String>();
			u.getExt_attr().put("SETTING", setting);
			
			InteractionOutput.output("tv","你是初次使用，需要完成一些配置。请设置我的性别。");
			str = scnner.nextLine();
			str = str.matches(".*(男|man|爷们).*")?"男":"女";
			InteractionOutput.output("tv","系统提示：你选择的是"+str+"，我的发音和性格将按"+str+"性设置。");
			setting.put("sex",str);
			
			InteractionOutput.output("tv","请设置我的年龄。");
			setting.put("age",getAgeFrom(scnner));
			
			InteractionOutput.output("tv","好了，现在给我起个名字吧。");
			str = scnner.nextLine();
			setting.put("name",str);
			
			//RandomUtil.random_username()
		}else{
			
		}
		return u;
	}

	private static String getAgeFrom(Scanner scnner) {
		String age = null;
		for (int i = 0; i < 3; i++) {
			if(StringUtil.isNull(age)){
				age = parseAge(scnner.nextLine());
			}
		}
		if(age == null){
			age = "21";
		}
		
		InteractionOutput.output("tv","系统提示:已设置年龄为"+age+"岁。如果以后需要修改，请告诉我'修改年龄为N岁'。");
		
		return age;
	}

	public static String parseAge(String str) {
		String age = null;
		
		Matcher matcher = Pattern.compile("([一二三四五六七八九十百]+)").matcher(str); // 匹配“二十”
		if (matcher.find()) {
			str = matcher.group(1);
			age = StringUtil.chineseToNum(str).toString();
		}else{
			matcher = Pattern.compile("(\\d+)").matcher(str); // 匹配“19”
			if (matcher.find()) {
				age = matcher.group(1);
			}else{
				InteractionOutput.output("tv","请告诉我年龄，如21岁。");
			}
		}
		
		return age;
	}

	private static User loadUser(String name2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 将用户持久化到DB
	 * 
	 * @param name
	 * @return
	 */
	public static void saveUserIns(User u) {
		// TODO
		// 1.保存聊天记录
		// 2.更新UserType,标签,兴趣,风格
		// 3.[提取用户扩展属性ext_attr]

	}

	public static void saveAll(User user) {
		// TODO Auto-generated method stub

	}

}
