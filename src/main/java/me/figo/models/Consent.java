package me.figo.models;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Consent {

	public Consent(boolean recurring, int period, List<String> scopes, Date expires_at) {
		super();
		this.recurring = recurring;
		this.period = period;
		this.scopes = scopes;
		this.expires_at = expires_at;
	}

	@Expose
	boolean recurring;

	@Expose
	int period;

	@Expose
	List<String> scopes;

	@Expose
	Date expires_at;

	public boolean isRecurring() {
		return recurring;
	}

	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public List<String> getScopes() {
		return scopes;
	}

	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}

	public Date getExpires_at() {
		return expires_at;
	}

	public void setExpires_at(Date expires_at) {
		this.expires_at = expires_at;
	}

}
