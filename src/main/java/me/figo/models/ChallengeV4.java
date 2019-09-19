package me.figo.models;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ChallengeV4 {

	// {additional_info=, created_at=2019-07-08T09:19:57.701535Z,
	// data=Please provide the TAN sent to your mobile phone, format=TEXT, id=1.0,
	// input_format=characters, label=TAN, max_length=6.0, min_length=6.0,
	// type=EMBEDDED, version=}
	@Expose
	private String additional_info;

	@Expose
	private Date created_at;
	
	@Expose
	private String data;
	
	@Expose
	private String format;
	
	@Expose
	private String id;

	@Expose
	private String input_format;

	@Expose
	private String label;

	@Expose
	private String max_length;

	@Expose
	private String min_length;

	// @TODO maybe introduce enum here
	@Expose
	private String type;

	@Expose
	private List<AuthMethod> auth_methods;
	
	@Expose
	private String version;

	public String getAdditional_info() {
		return additional_info;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public String getData() {
		return data;
	}

	public String getFormat() {
		return format;
	}

	public String getInput_format() {
		return input_format;
	}

	public String getLabel() {
		return label;
	}

	public String getMax_length() {
		return max_length;
	}

	public String getMin_length() {
		return min_length;
	}

	public String getType() {
		return type;
	}

	public String getVersion() {
		return version;
	}
	
	public static class ChallengesResponse {

		@Expose
		public List<ChallengeV4> challenges;

		public List<ChallengeV4> getChallenges() {
			return challenges;
		}

	}

	public String getId() {
		return id;
	}

	public List<AuthMethod> getAuthMethods() {
		return auth_methods;
	}
}
