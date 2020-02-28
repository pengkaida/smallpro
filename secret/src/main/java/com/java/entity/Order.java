package com.java.entity;

import java.util.List;

public class Order {

	private int id;

	private String orderNum;
	
	private List<Food> foodList;

	private AddresseeInfo addr;

	private int totalPrice;

	public Order(int id) {
		super();
		this.id = id;
	}


	public Order(List<Food> foodList, AddresseeInfo addr, int totalPrice) {
		super();
		this.foodList = foodList;
		this.addr = addr;
		this.totalPrice = totalPrice;
	}


	public Order() {
		// TODO Auto-generated constructor stub
	}

	public String getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Order [orderNum=" + orderNum + ", foodList=" + foodList + ", addr=" + addr + ", totalPrice="
				+ totalPrice + "]";
	}


	public List<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}

	public AddresseeInfo getAddr() {
		return addr;
	}

	public void setAddr(AddresseeInfo addr) {
		this.addr = addr;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
