package com.java.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.java.entity.Order;


public interface OrderMapper {

	public int getPriceById(String orderId);
	
	public void changeStutaByOrderNum(String num,int stuta);
	
	public void makeOrder(String orderNumber,int orederStutas,int addressId, int price,int accountId);

	public void makeOrderDetail(Map<String, Object> params);

	public List<Order> getAllOrder();
	
	public List<Order> getOrderListById(int id);
	
	public void changeStutaByid(int id,int stuta);
	
	public void changeStutaByNumList(ArrayList<String> list);
}
