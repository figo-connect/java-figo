package me.figo.models;

import com.google.gson.annotations.Expose;

public class Credential {
	
	@Expose
	private String key;
	
	@Expose
	private String label;
	
	@Expose
	private Integer max_length;
	
	@Expose
	private Integer min_length;
	
	@Expose
	private boolean is_optional;
	
	@Expose
	private boolean is_secret;
	
	public Credential()	{
		
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isOptional() {
		return is_optional;
	}

	public void setOptional(boolean optional) {
		this.is_optional = optional;
	}

	public boolean isSecret() {
		return is_secret;
	}

	public void setSecret(boolean secret) {
		this.is_secret = secret;
	}

	public String getKey() {
		return key;
	}

	public Integer getMaxLength() {
		return max_length;
	}

	public Integer getMinLength() {
		return min_length;
	}

}
