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
	
	public List<String> getAvailableLanguages() {
		return available_languages;
	}
	public String getCurrentLanguage() {
		return current_language;
	}
}
