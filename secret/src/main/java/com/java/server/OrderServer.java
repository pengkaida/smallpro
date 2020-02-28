package com.java.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.dao.OrderMapper;
import com.java.entity.Order;

@Service
public class OrderServer {
	
	@Autowired
	OrderMapper orderMapper;
	
	@Transactional
	public void addOrderAndDetail(String orderNumber,int orederStutas,int addressId, int price,int accountId,Map<String, Object> params) {
		orderMapper.makeOrder(orderNumber, orederStutas, addressId, price, accountId);
		orderMapper.makeOrderDetail(params);
	}
	
	public int getPriceById(String orderId) {
		return orderMapper.getPriceById(orderId);
	}
	
	public List<Order> getAllOrder() {
		return orderMapper.getAllOrder();
	}
	
	public List<Order> getOrderListById(int id) {
		return orderMapper.getOrderListById(id);
	}
	
	public void changeStutaByid(int id,int status) {
		orderMapper.changeStutaByid(id,status);
	}
	
	public void changeStutaByOrderNum(String num,int stuta) {
		orderMapper.changeStutaByOrderNum(num, stuta);
	}
	
	public void changeStutaByNumList(ArrayList<String> list) {
		orderMapper.changeStutaByNumList(list);
	}
}
