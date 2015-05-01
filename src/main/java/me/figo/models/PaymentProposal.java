package me.figo.models;

import java.util.List;

import com.google.gson.annotations.Expose;

public class PaymentProposal {

	@Expose
	private String account_number;
	
	@Expose
	private String bank_code;
	
	@Expose
	private String name;
	
	public PaymentProposal()	{
		
	}

	public String getAccountNumber() {
		return account_number;
	}

	public void setAccountNumber(String accountNumber) {
		this.account_number = accountNumber;
	}

	public String getBankCode() {
		return bank_code;
	}

	public void setBankCode(String bankCode) {
		this.bank_code = bankCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static class PaymentProposalResponse	{
		@Expose
		private List<PaymentProposal> paymentProposals;
		
		public PaymentProposalResponse()	{
			
		}
		
		public List<PaymentProposal> getPaymentProposals()	{
			return paymentProposals;
		}
	}
}
