package me.figo;

import me.figo.internal.SetupAccountRequest;
import me.figo.internal.TaskStatusResponse;
import me.figo.internal.TaskTokenResponse;

public class FigoPinException extends Exception {

	private static final long serialVersionUID = -3142403349349283593L;
	private String bankCode;
	private String countryCode;
	private String loginName;
	private String pin;
	private String taskToken;
	
	public FigoPinException(SetupAccountRequest request)	{
		this.bankCode = request.getBankCode();
		this.countryCode = request.getCountry();
		this.loginName = request.getCredentials().get(0);
		this.pin = request.getCredentials().get(1);
	}
	
	public FigoPinException(String bankCode, String countryCode, String loginName, String pin, TaskTokenResponse taskToken)	{
		this.bankCode = bankCode;
		this.countryCode = countryCode;
		this.loginName = loginName;
		this.pin = pin;
		this.taskToken = taskToken.getTaskToken();
	}
	
	public String getBankCode() {
		return bankCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public String getLoginName() {
		return loginName;
	}
	public String getPin() {
		return pin;
	}
	public String getTaskToken()	{
		return this.taskToken;
	}
	
	
}