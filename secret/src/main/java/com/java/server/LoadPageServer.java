 package com.java.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.java.dao.AdressMapper;
import com.java.dao.FoodMapper;
import com.java.entity.AddresseeInfo;
import com.java.entity.Food;
import com.java.entity.SameSortFood;

@Service
public class LoadPageServer {

	@Autowired
	FoodMapper foodMapper;
	
	@Autowired
	AdressMapper adressMapper;
	
	public List<SameSortFood> loadUserPage() {
		List<SameSortFood> list = foodMapper.getAllSameFood();
		return list;
	}
	public AddresseeInfo loadUserPageAddr(String name) {
		AddresseeInfo addr = adressMapper.getAdressByName(name);
		if(addr == null) {
			addr = new AddresseeInfo();
			addr.setId(-1);
		}
		return addr;
	}
	
	//------
	
	public Map<String,Object> loadManegeFoodList(float sumOfAPage){
		float sum = foodMapper.getFoodSum();
		int page = (int) Math.ceil(sum/sumOfAPage);
		System.out.println(page);
		List<Food> list = foodMapper.selectFoodByLimitNum(0, 10);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("foodlist", list);
		return map;
	}
	
}
