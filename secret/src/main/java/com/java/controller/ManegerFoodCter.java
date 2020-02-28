package com.java.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.java.entity.Food;
import com.java.entity.FoodSort;
import com.java.server.FoodServer;
import com.java.server.LoadPageServer;
import com.java.server.PagingServer;
import com.java.util.Msg;

@Controller
public class ManegerFoodCter {

	@Autowired
	FoodServer foodServer;

	@Autowired
	PagingServer pagingServer;
	
	@Autowired
	LoadPageServer loadPageServer;
	
	
	//����ʳ����Ϣ����
	@ResponseBody
	@RequestMapping("/loadfoodlist")
	public Map<String,Object> loadManegeFoodList(){
		//10Ϊ��ʾ�����ʳƷ��Ϣ����
		return loadPageServer.loadManegeFoodList(10);
	}
	
	//��ҳ����
	@ResponseBody
	@RequestMapping("/gopaging")
	public Map<String,Object> loadFoodListByPageNum(@RequestParam String spageNum){
		System.out.println(spageNum + "������");
		int pageNum = Integer.parseInt(spageNum);
		return pagingServer.selectFoodByPageNum(pageNum, 10);
	}
	
	//������ķ�ҳ����
	@ResponseBody
	@RequestMapping("/gosamepaging")
	public Map<String,Object> loadSameFoodList(@RequestParam String spageNum,@RequestParam String searchSort){
		int pageNum = Integer.parseInt(spageNum);
		return pagingServer.selectFoodByPageNumAndSort(pageNum, 10, new Food(new FoodSort(searchSort,0))); 
	}
	
	//��������
	@ResponseBody
	@RequestMapping("/searchfood")
	public Map<String,Object> searchFood(@RequestBody Food food){
		return foodServer.serachFood(food);
	}	
	
	//ɾ��ʳ��
	@ResponseBody
	@RequestMapping("/deletefood")
	public Msg deletefood(@RequestParam String vm) {
		int id = Integer.parseInt(vm.substring(1, vm.length()));
		foodServer.deleteFoodById(id);
		return new Msg("msg", 100);
	}

	// ����ajax����ͼƬ
	@ResponseBody
	@RequestMapping("/ajaxupload")
	public Food ajaxGetImg(HttpServletRequest request, @RequestBody MultipartFile uploadfile)
	 {
		String name = (String) request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		String sort = request.getParameter("sort");
		String img = "https://pkd-picture.oss-cn-shenzhen.aliyuncs.com/" + name + ".jpg";
		try {
			Food food = foodServer.addFood(name, price, img, sort,uploadfile);
			return food;
		} catch (Exception e) {
			return new Food(-1);
		}
	}
	
	@ResponseBody
	@RequestMapping("/updatafood")
	public Food alterFoodInfo(HttpServletRequest request, @RequestBody MultipartFile uploadfile) {
		String name = (String) request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(name+price+uploadfile);
		try {
			Food goFoodHtml = foodServer.updataFood(new Food(id, name, price,null), uploadfile);
			System.out.println(goFoodHtml);
			return goFoodHtml;
		} catch (Exception e) {
			System.out.println("���쳣��");
			return new Food(0);
		} 
	}
}
