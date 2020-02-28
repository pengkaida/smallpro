package com.java.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.java.dao.AdressMapper;
import com.java.dao.FoodMapper;
import com.java.entity.Account;
import com.java.entity.AddresseeInfo;
import com.java.entity.Food;
import com.java.entity.Order;

@Service
public class PaymentServer {

	@Autowired
	FoodMapper foodMapper;

	@Autowired
	AdressMapper adressMapper;

	@Autowired
	OrderServer orderServer;

	public List<Food> getFoodInfoToList(List<Food> foodList) {
		int foodprice;
		for (int i = 0; i < foodList.size(); i++) {
			System.out.println(foodList.get(i).getFoodName());
			foodprice = foodMapper.getPriceByName(foodList.get(i).getFoodName());
			foodList.get(i).setFoodPrice(foodprice);
		}
		return foodList;
	}

	public Order makeOrder(List<Food> foodlist, Account ac, HttpServletRequest req) {

		List<Food> list = getFoodInfoToList(foodlist);

		Map<String, Object> params = new HashMap<String, Object>();

		AddresseeInfo addr = adressMapper.getAdressByName(ac.getUserName());

		int totalPrice = 0;

		Date day = new Date();

		String orderNumber = ac.getId() + "_" + String.valueOf(day.getTime()).substring(2);

		for (int i = 0; i < list.size(); i++) {
			totalPrice = totalPrice + list.get(i).getFoodPrice() * list.get(i).getSum();
		}
		params.put("foodlist", list);
		params.put("orderNumber", orderNumber);
		orderServer.addOrderAndDetail(orderNumber, 0, addr.getId(), totalPrice, ac.getId(), params);
		req.getSession().setAttribute("orderNumber", orderNumber);
		return new Order(list, addr, totalPrice);
	}

	
}
