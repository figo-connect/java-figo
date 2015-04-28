package me.figo;

import me.figo.internal.SetupAccountRequest;
import me.figo.internal.TaskStatusResponse;
import java.util.List;

public class FigoPinException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3142403349349283593L;
	private String bankCode;
	private String countryCode;
	private List<String> credentials;

	public FigoPinException(SetupAccountRequest request)	{
		this.bankCode = request.getBankCode();
		this.countryCode = request.getCountry();
        this.credentials = request.getCredentials();
	}
	
	public FigoPinException(String bankCode, String countryCode, List<String> credentials)	{
		this.bankCode = bankCode;
		this.countryCode = countryCode;
        this.credentials = credentials;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
    public List<String> getCredentials() {
        return credentials;
    }

	
}
