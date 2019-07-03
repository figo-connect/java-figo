package me.figo.internal;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class StartProviderSyncResponse {

	@Expose
	public String id;

	@Expose
	public String status;

	@Expose
	public Date created_at;

	@Expose
	public Date started_at;

	@Expose
	public Date ended_at;

	@Expose
	public Object challenge;

	@Expose
	public Object error;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getStarted_at() {
		return started_at;
	}

	public void setStarted_at(Date started_at) {
		this.started_at = started_at;
	}

	public Date getEnded_at() {
		return ended_at;
	}

	public void setEnded_at(Date ended_at) {
		this.ended_at = ended_at;
	}

	public Object getChallenge() {
		return challenge;
	}

	public void setChallenge(Object challenge) {
		this.challenge = challenge;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}
}
