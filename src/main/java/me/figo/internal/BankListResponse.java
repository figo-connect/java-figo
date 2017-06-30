package me.figo.internal;

import java.util.List;

import com.google.gson.annotations.Expose;

public class BankListResponse {

	public BankListResponse() {
		super();
	}

	@Expose
	private List<BankResponse> banks;

	public List<BankResponse> getBanks() {
		return banks;
	}

	public void setBanks(List<BankResponse> banks) {
		this.banks = banks;
	}

	@Override
	public String toString() {
		String result = "BankListResponse ";
		for (BankResponse br : banks) {
			result += "\n" + br.toString();
		}
		return result;
	}

}
