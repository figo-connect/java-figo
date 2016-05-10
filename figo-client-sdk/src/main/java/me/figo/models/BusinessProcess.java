package me.figo.models;

import java.util.List;

import com.google.gson.annotations.Expose;

public class BusinessProcess {

	@Expose
	private String email;
	
	@Expose
	private String password;
	
	@Expose
	private String state;
	
	@Expose
	private List<ProcessStep> steps;
	
	public void addStep(ProcessStep step)	{
		this.steps.add(step);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<ProcessStep> getSteps() {
		return steps;
	}

	public void setSteps(List<ProcessStep> steps) {
		this.steps = steps;
	}
	
	
}
