package com.leo.model;

/**
 * 用户实体
 * @author leo
 *
 */
public class User {
	private int userId;
	private String userName;
	private String password;
	private String nickName;
	private String iconName;
	private String mood;
	
	
	public User() {
		super();
	}
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
	
}
