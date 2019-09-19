/**
 * 
 */
package me.figo.models;

import java.util.Map;

import com.google.gson.annotations.Expose;

/**
 * @author Daniel
 *
 */
public class AuthMethod {

	@Expose
	private String id;

	@Expose
	private String medium_name;

	@Expose
	private String type;

	@Expose
	private Map<String,String> additional_info;

	public String getId() {
		return id;
	}

	public String getMedium_name() {
		return medium_name;
	}

	public String getType() {
		return type;
	}

	public Map<String, String> getAdditional_info() {
		return additional_info;
	}

}
