package com.java.dao;

import java.util.List;
import java.util.Map;

import com.java.entity.Food;
import com.java.entity.FoodSort;
import com.java.entity.SameSortFood;

public interface FoodMapper {

	public int getPriceByName(String foodname);
	
	public int getSameSortFoodSum(String sort);
	
	public List<Food> getFoodByNameOrSort(Map<String,Object> map);
	
	public SameSortFood getSameFoodById(int id);
	
	public List<SameSortFood> getAllSameFood();
	
	public int selectFoodIdByName(String name);
	
	public void addFood(String name,int price,String img,int sort);
	
	public void addFoodReturnId(Food food);
	
	public void addFoodSortReturnId(FoodSort sort);
	
	public int isExistSort(String sortName);
	
	public List<Food> selectFoodByLimitNum(int begenNum,int limitNum);
	
	public int getFoodSum();
	
	public void deleteFoodById(int id);

	public Food getFoodById(int id);
	
	public String getFoodNameByid(int id);
	
	public int updataFood(Food food);
	
}
