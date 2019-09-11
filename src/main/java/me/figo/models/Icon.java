package me.figo.models;

import java.util.Map;

import com.google.gson.annotations.Expose;

public class Icon {
	
	@Expose
	private Map<String,String> resolutions;
	
	@Expose
	private String url;

	public Map<String, String> getResolutions() {
		return resolutions;
	}

	public String getUrl() {
		return url;
	}
}