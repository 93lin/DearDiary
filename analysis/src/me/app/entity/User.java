package me.app.entity;

public class User {
	private String username;
	private String password;
	public User(String ... str) {
		this.username = str[0];
		this.password = str[1];
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
