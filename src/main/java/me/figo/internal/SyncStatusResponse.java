package me.figo.internal;

import java.util.Date;

import com.google.gson.annotations.Expose;

import me.figo.models.ChallengeV4;

public class SyncStatusResponse {

	@Expose
	public String id;

	// @TODO maybe introduce enum here
	@Expose
	public String status;

	@Expose
	public Date created_at;

	@Expose
	public Date started_at;

	@Expose
	public Date ended_at;

	@Expose
	public ChallengeV4 challenge;

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

	public Date getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(Date created_at) {
		this.created_at = created_at;
	}

	public Date getStartedAt() {
		return started_at;
	}

	public void setStartedAt(Date started_at) {
		this.started_at = started_at;
	}

	public Date getEndedAt() {
		return ended_at;
	}

	public void setEndedAt(Date ended_at) {
		this.ended_at = ended_at;
	}

	public Object getChallenge() {
		return challenge;
	}

	public void setChallenge(ChallengeV4 challenge) {
		this.challenge = challenge;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}
}
