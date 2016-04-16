package me.shizh.ai.zen.user;

import java.util.Map;

import me.shizh.ai.zen.interaction.Interaction;
import me.shizh.common.util.RandomUtil;

public class User {
	private String uid;
	private String name;// name
	private String sex;
	private int age = 28;
	private String emotion = "快乐";//快乐、悲伤、恐惧、愤怒、焦虑、惊讶、尴尬、羞耻、内疚、鄙夷、厌恶、狂喜、绝望、暴怒、憎恶、欲望、悲哀、害羞、骄傲、自信、惊喜、犹豫、忧愁、忧郁、激动
	private UserType userType;
	private Map<String, String>[] ext_attr; // 用户扩展属性
	private Interaction interaction;

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

	public Map<String, String>[] getExt_attr() {
		return ext_attr;
	}

	public void setExt_attr(Map<String, String>[] ext_attr) {
		this.ext_attr = ext_attr;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}


	/**
	 * 随机名称
	 * 
	 * @return
	 */
	public static User initUserIns() {
		return initUserIns(RandomUtil.random_username());
	}

	/**
	 * 返回uid为name的User对象实例 先从DB查找历史记录，若存在，从DB加载，不存在创建新用户.
	 * 
	 * @param name
	 * @return
	 */
	public static User initUserIns(String name) {
		User u = new User();
		u.setUid(name);

		return u;
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
