package me.figo;

import me.figo.internal.SetupAccountRequest;

public class FigoPinException extends Exception {

    private static final long serialVersionUID = -3142403349349283593L;
	private final String bankCode;
	private final String countryCode;
	private final String loginName;
	private final String pin;
	
	public FigoPinException(SetupAccountRequest request)	{
		this.bankCode = request.getBankCode();
		this.countryCode = request.getCountry();
		this.loginName = request.getCredentials().get(0);
		this.pin = request.getCredentials().get(1);
	}
	
	public FigoPinException(String bankCode, String countryCode, String loginName, String pin)	{
		this.bankCode = bankCode;
		this.countryCode = countryCode;
		this.loginName = loginName;
		this.pin = pin;
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
	
	
}
