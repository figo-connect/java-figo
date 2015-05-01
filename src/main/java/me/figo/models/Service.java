package me.figo.models;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Service {

	@Expose
	private String name;
	
	@Expose
	private String bank_code;
	
	@Expose
	private String icon;
	
	@Expose
	private HashMap<String, String> additional_icons;
	
	public Service()	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankCode() {
		return bank_code;
	}

	public void setBankCode(String bankCode) {
		this.bank_code = bankCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public HashMap<String, String> getAdditionalIcons() {
		return additional_icons;
	}

	public void setAdditional_icons(HashMap<String, String> additionalIcons) {
		this.additional_icons = additionalIcons;
	}
	
	public static class ServiceResponse	{
		
		@Expose
		private List<Service> services;
		
		public ServiceResponse()	{
			
		}
		
		public List<Service> getServices()	{
			return services;
		}
	}
	
}
