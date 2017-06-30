package me.figo.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

import me.figo.models.BankLoginSettings;
import me.figo.models.Credential;

public class BankResponse {

	@Expose
	private String advice;
	@Expose
	private String bank_code;
	@Expose
	private String bank_name;
	@Expose
	private String bic;
	@Expose
	private List<Credential> credentials;
	@Expose
	private List<Object> icon;

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public List<Credential> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<Credential> credentials) {
		this.credentials = credentials;
	}

	@Override
	public String toString() {
		return "BankResponse [advice=" + advice + ", bank_code=" + bank_code + ", bank_name=" + bank_name + ", bic="
				+ bic + "]";
	}

	public List<Object> getIcon() {
		return icon;
	}

	public void setIcon(List<Object> icon) {
		this.icon = icon;
	}

	public BankLoginSettings getAsBankLoginSetting() {
		BankLoginSettings bls = new BankLoginSettings();
		bls.setAdvice(this.advice);
		bls.setBankName(this.bank_name);
		bls.setCredentials(this.credentials);
		if (this.getIcon() != null) {
			if (this.getIcon().size() > 0)
				// first entry is the main icons url
				bls.setIcon((String) this.getIcon().get(0));
			if (this.getIcon().size() > 1) {
				// second entry is a map of additional icons
				Map<String, String> map = (Map) this.getIcon().get(1);
				HashMap<String, String> additionalIcons = new HashMap<>();
				additionalIcons.putAll(map);
				bls.setAdditionalIcons(additionalIcons);
			}
		}
		return bls;
	}

}
