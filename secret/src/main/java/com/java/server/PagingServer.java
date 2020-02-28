package com.java.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.java.dao.FoodMapper;
import com.java.entity.Food;

@Service
public class PagingServer {

	@Autowired
	FoodMapper foodMapper;
	
	public Map<String,Object> selectFoodByPageNum(int pageNum,int displayNum){
		int originNum = displayNum *(pageNum - 1);
		int finishNum = displayNum;
		List<Food> list = foodMapper.selectFoodByLimitNum(originNum, finishNum);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", -1);
		map.put("foodlist", list);
		return map;
	}
	
	public Map<String,Object> selectFoodByPageNumAndSort(int pageNum,int displayNum,Food food){
		int originNum = displayNum *(pageNum - 1);
		int finishNum = displayNum;
		Map<String,Object> goodmap = new HashMap<String,Object>();
		goodmap.put("food",food);
		goodmap.put("startNum",originNum);
		goodmap.put("endNum",finishNum);
		List<Food> list = foodMapper.getFoodByNameOrSort(goodmap);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", -1);
		map.put("foodlist", list);
		return map;
	}
	
}
