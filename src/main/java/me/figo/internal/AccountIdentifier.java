package me.figo.internal;

import com.google.gson.annotations.Expose;

public class AccountIdentifier {

	@Expose
	private String id;

	@Expose
	private String currency;

	public AccountIdentifier(String id, String currency) {
		super();
		this.id = id;
		this.currency = currency;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
