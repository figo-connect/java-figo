package me.figo.internal;

import java.util.Map;

import com.google.gson.annotations.Expose;

import me.figo.models.Consent;

public class AddProviderAccessRequest {

	@Expose
	private String access_method_id;
	
	@Expose
	private boolean save_credentials;

	@Expose
	private Map<String,String> credentials;

	@Expose
	private Consent consent;

	public AddProviderAccessRequest(String access_method_id, AccountIdentifier account_identifier,
			boolean save_credentials,
			Map<String, String> credentials, Consent consent) {
		super();
		this.access_method_id = access_method_id;
		this.credentials = credentials;
		this.consent = consent;
	}

	public String getAccessMethodId() {
		return access_method_id;
	}

	public void setAccessMethodId(String accessMethodId) {
		this.access_method_id = accessMethodId;
	}

	public boolean isSave_credentials() {
		return save_credentials;
	}

	public void setSave_credentials(boolean save_credentials) {
		this.save_credentials = save_credentials;
	}

	public Consent getConsent() {
		return consent;
	}

	public void setConsent(Consent consent) {
		this.consent = consent;
	}

	public String getAccess_method_id() {
		return access_method_id;
	}

	public void setAccess_method_id(String access_method_id) {
		this.access_method_id = access_method_id;
	}

	public Map<String, String> getCredentials() {
		return credentials;
	}

	public void setCredentials(Map<String, String> credentials) {
		this.credentials = credentials;
	}

}
