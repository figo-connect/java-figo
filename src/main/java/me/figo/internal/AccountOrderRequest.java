package me.figo.internal;

import java.util.List;

import me.figo.models.Account;

import com.google.gson.annotations.Expose;

public class AccountOrderRequest {
	
	@Expose
	private List<String> accounts;
	
	public AccountOrderRequest()	{
		
	}
	
	public AccountOrderRequest(List<Account> orderedList)	{
		for(Account account : orderedList)	{
			accounts.add(account.getAccountId());
		}
	}

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
		
}
