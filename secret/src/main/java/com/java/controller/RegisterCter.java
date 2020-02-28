package com.java.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.entity.Account;
import com.java.server.RegisterServer;
import com.java.util.Msg;

@Controller
public class RegisterCter {

	@Autowired
	RegisterServer registerServer;
	
	@ResponseBody
	@RequestMapping("/accregister")
	public Msg registerAccount(@RequestBody @Valid Account account,BindingResult result) {
		if(result.hasErrors()) {
			return new Msg("×¢²áÊ§°Ü", 400);
		}else {
			return registerServer.registerAccount(account);
		}
	}
}
