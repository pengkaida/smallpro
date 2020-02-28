package com.java.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.entity.Order;
import com.java.server.OrderServer;
import com.java.util.Msg;

@Controller
public class ManegerOrderCter {

	@Autowired
	OrderServer orderServer;

	// ���ض���
	@ResponseBody
	@RequestMapping("/getorderlist")
	public List<Order> getOrder() {
		return orderServer.getAllOrder();
	}

	// ��ӡ��������
	@ResponseBody
	@RequestMapping("/finishOrder")
	public Msg finishOrder(@RequestParam String id) {
		orderServer.changeStutaByid(Integer.parseInt(id), 2);
		return new Msg();
	}

	// ��ӡ��������
	@ResponseBody
	@RequestMapping("/finishOrders")
	public Msg finishOrders(@RequestParam ArrayList<String> ordernums) {
		orderServer.changeStutaByNumList(ordernums);
		return new Msg();
	}

	// ʵʱ��ȡ����
	@ResponseBody
	@RequestMapping("/getRealTimeOrder")
	public List<Order> RealTimeOrder(@RequestParam int id) {
		return orderServer.getOrderListById(id);
	}
}
