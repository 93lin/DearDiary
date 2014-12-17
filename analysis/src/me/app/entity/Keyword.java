package me.app.entity;

public class Keyword {

	private int number = 0;
	private String name;
	public Keyword() {
		this.number = 0;
		this.name = "";
	}
	public Keyword(String na, int num) {
		setNumber(num);
		setName(na);
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
