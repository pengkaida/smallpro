package com.java.dao;

import java.util.Map;

import com.java.entity.AddresseeInfo;

public interface AdressMapper {

	public AddresseeInfo getAdressByName(String username);//获取收件人默认地址
	
	public void setUserAddress(Map<String, Object> params);
	
}
