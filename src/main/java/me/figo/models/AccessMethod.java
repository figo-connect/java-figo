package me.figo.models;

import java.util.List;

import com.google.gson.annotations.Expose;

public class AccessMethod {

	@Expose
	private String id;
	
	@Expose
	private boolean in_psd2_scope;
	
	@Expose
	private String type;
	
	@Expose
	private List<String> supported_account_types;
	
	@Expose
	private boolean configurable_consent;
	
	@Expose
	private boolean requires_account_identifiers;
	
	@Expose
	private List<String> customer_authentication_flows;

	@Expose
	private String advice;

	@Expose
	private List<Credential> credentials;
	
	public List<Credential> getCredentials() {
		return credentials;
	}

	public String getAdvice() {
		return advice;
	}

	public String getId() {
		return id;
	}

	public boolean isIn_psd2_scope() {
		return in_psd2_scope;
	}

	public String getType() {
		return type;
	}

	public List<String> getSupported_account_types() {
		return supported_account_types;
	}

	public boolean isConfigurable_consent() {
		return configurable_consent;
	}

	public boolean isRequires_account_identifiers() {
		return requires_account_identifiers;
	}

	public List<String> getCustomer_authentication_flows() {
		return customer_authentication_flows;
	}

}
