package me.figo.models;

import com.google.gson.annotations.Expose;

public class Challenge {

	@Expose
	private String title;
	
	@Expose
	private String label;
	
	@Expose
	private String format;
	
	@Expose
	private String data;

	public String getTitle() {
		return title;
	}

	public String getLabel() {
		return label;
	}

	public String getFormat() {
		return format;
	}

	public String getData() {
		return data;
	}
	
}
