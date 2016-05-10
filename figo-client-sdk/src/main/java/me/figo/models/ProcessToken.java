package me.figo.models;

import com.google.gson.annotations.Expose;

public class ProcessToken {
	
	@Expose
	private String process_token;

	public String getProcessToken() {
		return process_token;
	}

	public void setProcessToken(String processToken) {
		this.process_token = processToken;
	}
	
}
