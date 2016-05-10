package me.figo.internal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskStatusRequest {

	@Expose
	private String id;
	
	@Expose
	private String pin;
	
	@SerializedName("continue")
	@Expose
	private String _continue;
	
	@Expose
	private String save_pin;
	
	@Expose
	private String response;
	
	public TaskStatusRequest(TaskTokenResponse tokenResponse)	{
		this.id = tokenResponse.task_token;
	}
	
	public TaskStatusRequest(String tokenId)	{
		this.id = tokenId;
	}
	
	public TaskStatusRequest(String tokenId, String pin)	{
		this.id = tokenId;
		this._continue = "1";
		this.pin = pin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getContinue() {
		return _continue;
	}

	public void setContinue(String _continue) {
		this._continue = _continue;
	}

	public String getSavePin() {
		return save_pin;
	}

	public void setSavePin(String save_pin) {
		this.save_pin = save_pin;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	
	
}
