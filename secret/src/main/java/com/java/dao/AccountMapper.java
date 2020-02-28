package com.java.dao;

import com.java.entity.Account;

public interface AccountMapper {

	public Account getAccountByName(String username);
	
	public int addAccount(Account account);
	
}
