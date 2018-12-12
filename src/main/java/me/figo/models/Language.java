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
	private List<String> available_languages;

	@Expose
	private String current_language;
	
	public List<String> getAvailable_languages() {
		return available_languages;
	}
	public String getCurrent_language() {
		return current_language;
	}
}
