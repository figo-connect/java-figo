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

	public String getAccess_method_id() {
		return access_method_id;
	}

	public void setAccess_method_id(String access_method_id) {
		this.access_method_id = access_method_id;
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

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
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
