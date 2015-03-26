package me.figo.internal;

import me.figo.models.Challenge;

import com.google.gson.annotations.Expose;

public class TaskStatusResponse {

	@Expose
	private String account_id;
	
	@Expose
	private String message;
	
	@Expose
	private boolean is_waiting_for_pin;
	
	@Expose
	private boolean is_waiting_for_response;
	
	@Expose
	private boolean is_erroneous;
	
	@Expose
	private boolean is_ended;
	
	@Expose
	private Challenge challenge;

	public String getAccountId() {
		return account_id;
	}

	public String getMessage() {
		return message;
	}

	public boolean isWaitingForPin() {
		return is_waiting_for_pin;
	}

	public boolean isWaitingForResponse() {
		return is_waiting_for_response;
	}

	public boolean isErroneous() {
		return is_erroneous;
	}

	public boolean isEnded() {
		return is_ended;
	}

	public Challenge getChallenge() {
		return challenge;
	}
	
	
	
}
