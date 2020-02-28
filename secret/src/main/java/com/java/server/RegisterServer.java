package com.java.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dao.AccountMapper;
import com.java.entity.Account;
import com.java.util.Msg;

@Service
public class RegisterServer {

	@Autowired
	AccountMapper accountMapper;
	
	public Msg registerAccount(Account account) {
		
		Account acc = accountMapper.getAccountByName(account.getUserName());
		if(acc==null)
		{
			try {
				accountMapper.addAccount(account);
			} catch (Exception e) {
				return new Msg("ע��ʧ�ܣ����Ժ�����", 400);
			}
			return new Msg("ע��ɹ�", 100);
		}
		return new Msg("ע��ʧ��,�˺��ѱ�ע��", 400);
	}
	
	
}
