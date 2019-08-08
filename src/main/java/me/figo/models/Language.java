/**
 * 
 */
package me.figo.models;

import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * @author Daniel
 *
 */
public class Language {

	@Expose
	private List<String> available;

	@Expose
	private String current;
	
	public List<String> getAvailable() {
		return available;
	}
	public String getCurrent() {
		return current;
	}
}
