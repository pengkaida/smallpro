package com.java.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.entity.Account;
import com.java.server.LoginServer;
import com.java.util.Msg;

@Controller
public class LoginCter {

	@Autowired
	LoginServer loginServer;
	
	@Autowired
	Msg msg;
	
	@ResponseBody
	@RequestMapping("/judge")
	public Msg judgeAccount(HttpServletRequest req,@RequestBody Account account) {
		
		int istrue = loginServer.isCorrectLogin(account);
		
		if(istrue > 0) {
			String newPassword=DigestUtils.md5DigestAsHex(account.getPassword().getBytes());
			account.setPassword(newPassword);
			account.setId(istrue);
			req.getSession().setAttribute("token",account);
			return new Msg("", 100);
		}
		return msg;
	}

	@RequestMapping("/correct")
	public String hasAccount(HttpServletRequest request) {
		Account account =  (Account) request.getSession().getAttribute("token");
		if(account == null)
		{
			return "success";
		}
		if(loginServer.hasAccount(account))
		{
			return "goodsmenu";
		}else {
			return "success";
		}
	}
	
	@Bean
	public Msg getMsg() {
		return new Msg("账号密码不正确，请重新输入", 404);
	}
}
