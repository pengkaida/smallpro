package com.java.dao;

import java.util.Map;

import com.java.entity.AddresseeInfo;

public interface AdressMapper {

	public AddresseeInfo getAdressByName(String username);//��ȡ�ռ���Ĭ�ϵ�ַ
	
	public void setUserAddress(Map<String, Object> params);
	
}
