package me.figo.models;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

public class ProcessOption {

	@Expose
	private String account_number;
	
	@Expose
	private BigDecimal amount;

	@Expose
	private String bank_code;
	
	@Expose
	private String currency;
	
	@Expose
	private String name;
	
	@Expose
	private String purpose;
	
	@Expose
	private String type;

	public String getAccountNumber() {
		return account_number;
	}

	public void setAccountNumber(String accountNumber) {
		this.account_number = accountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBankCode() {
		return bank_code;
	}

	public void setBankCode(String bankCode) {
		this.bank_code = bankCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
