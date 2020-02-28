package com.java.entity;

public class Food {

	private int id;
	
	private String foodName;
	
	private int foodPrice;
	
	private int sum;//ÊýÁ¿
	
	private String foodImg;
	
	private FoodSort foodSort;

	public Food(int id, String foodName, int foodPrice, String foodImg, FoodSort foodSort) {
		super();
		this.id = id;
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.foodImg = foodImg;
		this.foodSort = foodSort;
	}
	
	public Food(int id, String foodName, int foodPrice, String foodImg) {
		super();
		this.id = id;
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.foodImg = foodImg;
	}

	public Food(int id, String foodName, int foodPrice, int sum, String foodImg, FoodSort foodSort) {
		super();
		this.id = id;
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.sum = sum;
		this.foodImg = foodImg;
		this.foodSort = foodSort;
	}

	public Food(int id)
	{
		this.id = id;
	}
	public void setFoodSort(FoodSort foodSort) {
		this.foodSort = foodSort;
	}
	public FoodSort getFoodSort() {
		return foodSort;
	}

	public String getFoodImg() {
		return foodImg;
	}

	public void setFoodImg(String foodImg) {
		this.foodImg = foodImg;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(int foodPrice) {
		this.foodPrice = foodPrice;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", foodName=" + foodName + ", foodPrice=" + foodPrice + ", sum=" + sum + ", foodImg="
				+ foodImg + ", foodSort=" + foodSort + "]";
	}

	public Food(FoodSort foodSort) {
		this.foodSort = foodSort;
	}
	public Food() {
		// TODO Auto-generated constructor stub
	}

	
}
