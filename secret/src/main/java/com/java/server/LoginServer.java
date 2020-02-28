package com.java.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.java.dao.AccountMapper;
import com.java.entity.Account;

@Service
public class LoginServer {

	@Autowired
	AccountMapper accountMapper;
	
	public int isCorrectLogin(Account account) {
		String username = account.getUserName();
		Account db_account = accountMapper.getAccountByName(username);
		if (db_account == null) {
			return -1;
		}
		if (account.getPassword().equals(db_account.getPassword()) ) {
			return db_account.getId();
		}
		return -1;
	}
	
	public boolean hasAccount(Account account) {
		String username = account.getUserName();
		Account db_account = accountMapper.getAccountByName(username);
		String db_password = DigestUtils.md5DigestAsHex(db_account.getPassword().getBytes());
		if(account.getPassword().equals(db_password))
		{
			return true;
		}	
		return false;
	}

}
