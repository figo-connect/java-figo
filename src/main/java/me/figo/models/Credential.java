package me.figo.models;

import com.google.gson.annotations.Expose;

public class Credential {
	
	@Expose
	private String label;
	
	@Expose
	private boolean masked;
	
	@Expose
	private boolean optional;
	
	public Credential()	{
		
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isMasked() {
		return masked;
	}

	public void setMasked(boolean masked) {
		this.masked = masked;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}
