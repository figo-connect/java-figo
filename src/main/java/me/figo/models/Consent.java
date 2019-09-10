package me.figo.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Consent {

	public static class ConsentAccount {
		@Expose
		String id;

		@Expose
		String currency;

		public String getId() {
			return id;
		}

		public String getCurrency() {
			return currency;
		}

		public ConsentAccount(String id, String currency) {
			super();
			this.id = id;
			this.currency = currency;
		}
		
	}

	public Consent(boolean recurring, int period, List<String> scopes) {
		super();
		this.recurring = recurring;
		this.period = period;
		this.scopes = scopes;
	}

	@Expose
	boolean recurring;

	@Expose
	int period;

	@Expose
	List<String> scopes;

	@Expose
	List<ConsentAccount> accounts;
	
	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	public List<ConsentAccount> getAccounts() {
		return accounts==null?new ArrayList<ConsentAccount>():accounts;
	}

	public void setAccounts(List<ConsentAccount> accounts) {
		this.accounts = accounts;
	}

}
