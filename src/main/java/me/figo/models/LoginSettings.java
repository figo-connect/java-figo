package me.figo.models;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;

public class LoginSettings {
	
	@Expose
	private String bank_name;
	
	@Expose
	private boolean supported;
	
	@Expose
	private String icon;
	
	@Expose
	private HashMap<String, String> additional_icons;
	
	@Expose
	private List<Credential> credentials;
	
	@Expose
	private String auth_type;
	
	@Expose
	private String advice;
	
	public LoginSettings()	{
		
	}

	public String getBankName() {
		return bank_name;
	}

	public void setBankName(String bankName) {
		this.bank_name = bankName;
	}

	public boolean isSupported() {
		return supported;
	}

	public void setSupported(boolean supported) {
		this.supported = supported;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public HashMap<String, String> getAdditionalIcons() {
		return additional_icons;
	}

	public void setAdditionalIcons(HashMap<String, String> additionalIcons) {
		this.additional_icons = additionalIcons;
	}

	public List<Credential> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<Credential> credentials) {
		this.credentials = credentials;
	}

	public String getAuthType() {
		return auth_type;
	}

	public void setAuthType(String authType) {
		this.auth_type = authType;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
}
