package com.java.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dao.AdressMapper;
import com.java.entity.Account;
import com.java.entity.AddresseeInfo;

@Service
public class AdrressServer {

	@Autowired
	AdressMapper adressMapper;
	
	public AddresseeInfo changeAdrress(AddresseeInfo info,Account account) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", account.getUserName());
		params.put("addr", info);
		adressMapper.setUserAddress(params);
		return info;
	}
	
	public AddresseeInfo selectAddressByUname(String username) {
		return adressMapper.getAdressByName(username);
	}
}
