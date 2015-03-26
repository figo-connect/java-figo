package me.figo.models;

import java.util.List;

import com.google.gson.annotations.Expose;

public class PaymentContainer {
	
	@Expose
	private String account_id;
	
	@Expose
	private String type;
	
	@Expose
	private List<Payment> container;
	
	public PaymentContainer(List<Payment> paymentList)	{
		this.account_id = paymentList.get(0).getAccountId();
		this.type = paymentList.get(0).getType();
		this.container = paymentList;
	}

	public String getAccountId() {
		return account_id;
	}

	public void setAccountId(String accountId) {
		this.account_id = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Payment> getContainer() {
		return container;
	}

	public void setContainer(List<Payment> container) {
		this.container = container;
	}
	
	public void addPayment(Payment payment)	{
		this.container.add(payment);
	}

}
