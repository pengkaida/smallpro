package com.java.entity;

public class FoodSort {

	private String sortName;

	private int sortid;
	
	public FoodSort() {
		
	}
	
	public FoodSort(String sortName, int sortid) {
		this.sortName = sortName;
		this.sortid = sortid;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public int getSortid() {
		return sortid;
	}

	public void setSortid(int sortid) {
		this.sortid = sortid;
	}
	@Override
	public String toString() {
		return "FoodSort [sortName=" + sortName + ", sortid=" + sortid + "]";
	}
}
