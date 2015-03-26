package me.figo.models;

import java.util.List;

import com.google.gson.annotations.Expose;

public class ProcessStep {
	
	@Expose
	private List<ProcessOption> options;
	
	@Expose
	private String type;
	
	public void addOption(ProcessOption option)	{
		this.options.add(option);
	}

	public List<ProcessOption> getOptions() {
		return options;
	}

	public void setOptions(List<ProcessOption> options) {
		this.options = options;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
