package com.java.server;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.java.dao.FoodMapper;
import com.java.entity.Food;
import com.java.entity.FoodSort;
import com.java.util.OosUtil;

@Service
public class FoodServer {

	@Autowired
	FoodMapper foodMapper;

	@Autowired
	PagingServer pagingServer;

	@Autowired
	OosUtil oosUtil;

	public Food addFood(String name, int price, String img, String sort, MultipartFile uploadfile) throws Exception {
		Food food = new Food(0, name, price, img, new FoodSort(sort, 0));
		oosUtil.oosUpload(uploadfile, name, null);
		try {
			int id = foodMapper.isExistSort(sort);
			food.getFoodSort().setSortid(id);
			foodMapper.addFoodReturnId(food);
		} catch (Exception e) {
			FoodSort fs = new FoodSort(sort, 0);
			foodMapper.addFoodSortReturnId(fs);
			food.getFoodSort().setSortid(fs.getSortid());
			foodMapper.addFoodReturnId(food);
		}
		return food;
	}

	public void deleteFoodById(int id) {
		Food f = foodMapper.getFoodById(id);
		foodMapper.deleteFoodById(id);
		oosUtil.oosDelete(f.getFoodName(), null);
	}

	public Food updataFood(Food food, MultipartFile uploadfile) throws Exception {
		System.out.println("进来了吗");
		String DbFoodName = foodMapper.getFoodNameByid(food.getId());
		System.out.println("到这了");
		if (DbFoodName.equals(food.getFoodName())) {
			System.out.println("1");
			oosUtil.oosUpadte(uploadfile, DbFoodName, "");
		} else {
			System.out.println("2");
			oosUtil.oosUpadte(uploadfile, DbFoodName, food.getFoodName());
			String img = "https://pkd-picture.oss-cn-shenzhen.aliyuncs.com/" + food.getFoodName() + ".jpg";
			food.setFoodImg(img);
		}
		System.out.println("来了");
		int can = foodMapper.updataFood(food);
		if (can > 0) {
			System.out.println("改成功了");
			return foodMapper.getFoodById(food.getId());
		} else {
			System.out.println("改没了");
			return null;
		}
	}

	public Map<String, Object> serachFood(Food food) {
		int page;
		if (food.getFoodName() == "" || food.getFoodName() == null) {
			float foodsum = foodMapper.getSameSortFoodSum(food.getFoodSort().getSortName());
			page = (int) Math.ceil(foodsum / 10f);
		} else {
			page = 1;
		}
		System.out.println("page:" + page);
		Map<String, Object> map = pagingServer.selectFoodByPageNumAndSort(1, 10, food);
		map.put("page", page);
		return map;
	}

}
