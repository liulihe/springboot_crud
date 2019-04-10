package com.springboot.entity;

public class Desk {
	private String color;
	private Integer size;
	public Desk() {
		// TODO Auto-generated constructor stub
	}
	public Desk(String color, Integer size) {
		super();
		this.color = color;
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public Integer getSize() {
		return size;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "Desk [color=" + color + ", size=" + size + "]";
	}
}
