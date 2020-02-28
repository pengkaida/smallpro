package com.java.entity;

import java.util.List;

public class SameSortFood {

	private String sort;

	private List<Food> foodlist;

	public SameSortFood(String sort, List<Food> foodlist) {
		this.sort = sort;
		this.foodlist = foodlist;
	}
	
	public SameSortFood() {
		
	}

	@Override
	public String toString() {
		return "SameSortFood [sort=" + sort + ", foodlist=" + foodlist + "]";
	}


	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<Food> getFoodlist() {
		return foodlist;
	}

	public void setFoodlist(List<Food> foodlist) {
		this.foodlist = foodlist;
	}

}
