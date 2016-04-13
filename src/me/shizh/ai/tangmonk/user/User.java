package me.shizh.ai.tangmonk.user;

import java.util.Map;

import me.shizh.ai.tangmonk.interaction.Interaction;

public class User {
	private String uid;
	private UserType userType;
	private Map<String, String>[] ext_attr; // 用户扩展属性
	private Interaction interaction;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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



	/**
	 * 返回uid为name的User对象实例
	 * 先从DB查找历史记录，若存在，从DB加载，不存在创建新用户.
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
	 * @param name
	 * @return
	 */
	public static void saveUserIns(User u) {
		//TODO
		//1.保存聊天记录
		//2.更新UserType,标签,兴趣,风格
		//3.[提取用户扩展属性ext_attr]
		
	}
}
