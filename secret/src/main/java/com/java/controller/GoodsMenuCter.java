package com.java.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.java.entity.Account;
import com.java.entity.AddresseeInfo;
import com.java.entity.Food;
import com.java.entity.Order;
import com.java.entity.SameSortFood;
import com.java.server.AdrressServer;
import com.java.server.LoadPageServer;
import com.java.server.OrderServer;
import com.java.server.PaymentServer;
import com.java.util.Msg;

@Controller
public class GoodsMenuCter {

	@Autowired
	LoadPageServer loadPageServer;

	@Autowired
	PaymentServer paymentServer;

	@Autowired
	AdrressServer adrressServer;

	@Autowired
	OrderServer orderServer;

	/**
	 * ����menuҳ��ʳ����Ϣ
	 */
	@ResponseBody
	@RequestMapping("/loaduserpage")
	public List<SameSortFood> loadUserPage() {
		List<SameSortFood> list = loadPageServer.loadUserPage();
		return list;
	}

	/**
	 * ����menuҳ���ռ�����Ϣ
	 */
	@ResponseBody
	@RequestMapping("/loaduseraddr")
	public AddresseeInfo loadUserPageAddr(HttpServletRequest req) {
		Account account = (Account) req.getSession().getAttribute("token");
		String name = account.getUserName();
		AddresseeInfo info = loadPageServer.loadUserPageAddr(name);
		return info;
	}

	/**
	 * ��ӻ��޸ĵ�ַ
	 */
	@ResponseBody
	@RequestMapping("/addaddress")
	public AddresseeInfo addUserAddress(HttpServletRequest req, @RequestBody AddresseeInfo info) {
		Account account = (Account) req.getSession().getAttribute("token");
		System.out.println(account);
		adrressServer.changeAdrress(info, account);
		return info;
	}

	/**
	 * ���ﳵ����ʵ��
	 * 
	 * @param foodList �������Ʒ
	 * @return ���ض�����Ϣ
	 */
	@ResponseBody
	@RequestMapping("/calculate")
	public Order calculateSum(HttpServletRequest req, @RequestBody List<Food> foodList) {
		Account acc = (Account) req.getSession().getAttribute("token");
		AddresseeInfo adInfo = adrressServer.selectAddressByUname(acc.getUserName());
		System.out.println(adInfo);
		if (adInfo != null && !adInfo.getName().equals("") && !adInfo.getName().equals(null) && !adInfo.getAddress().equals("")
				&& !adInfo.getAddress().equals(null) && !adInfo.getPhone().equals("") && !adInfo.getPhone().equals(null)) {
			Order order = paymentServer.makeOrder(foodList, acc, req);
			System.out.println("��ʲô");
			return order;
		}else {
			System.out.println("��������");
			return new Order(-1);
		}
	}

	/**
	 * ֧���ж�ʵ�� ������
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/judgeprice")
	public Msg judgePrice(HttpServletRequest req, @RequestParam String paysum) {
		int pay = Integer.parseInt(paysum);
		String orderId = (String) req.getSession().getAttribute("orderNumber");
		int price = orderServer.getPriceById(orderId);
		System.out.println(price);
		if (pay == price) {
			System.out.println("��������");
			orderServer.changeStutaByOrderNum(orderId, 1);
			return new Msg("success", 100);
		} else {
			System.out.println("��������222");
			return new Msg("error", 400);
		}
	}
}
