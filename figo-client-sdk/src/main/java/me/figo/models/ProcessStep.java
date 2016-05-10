package me.figo.models;

import com.google.gson.annotations.Expose;

public class ProcessStep {
	
	@Expose
	private ProcessOption options;
	
	@Expose
	private String type;
	
	public void addOption(ProcessOption options)	{
		this.options = options;
	}

	public ProcessOption getOptions() {
		return options;
	}

	public void setOptions(ProcessOption options) {
		this.options = options;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
