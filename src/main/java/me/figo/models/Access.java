package me.figo.models;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Access {

	@Expose
	private String id;
	
	@Expose
	private String access_method_id;
	
	@Expose
	private boolean save_credentials;

	@Expose
	private Consent consent;

	@Expose
	private Date created_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(Date createdAt) {
		this.created_at = createdAt;
	}

	public Access() {
		
	}

	public static class AccessResponse {
		
		@Expose
		private List<Access> accesses;
		
		public AccessResponse() {
			
		}
		
		public List<Access> getAccesses() {
			return accesses;
		}
	}
	
}
