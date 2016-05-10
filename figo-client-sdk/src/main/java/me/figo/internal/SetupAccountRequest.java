package me.figo.internal;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class SetupAccountRequest {

	/**
	 * bank code to setup
	 */
	@Expose
	private String bank_code;
	
	/**
	 * country code of the bank
	 */
	@Expose
	private String country;
	
	/**
	 * List of bank credentials
	 */
	@Expose
	private List<String> credentials;
	
	/**
	 * List of tasks which should be executed while doing a sync
	 */
	@Expose
	private List<String> sync_tasks;
	
	/**
	 * use iban instead of bank_code
	 */
	@Expose
	private String iban;
	
	/**
	 * save the pin after setup
	 */
	@Expose
	private boolean save_pin;
	
	/**
	 * Disable the first sync - register the account only
	 */
	@Expose 
	private boolean disable_first_sync;
	
	/**
	 * the uri the user should be redirected
	 */
	@Expose 
	private String redirect_uri;

	/**
	 * 
	 * @param bankCode
	 * @param countryCode
	 * @param loginName
	 * @param pin
	 * @param sync_tasks
	 */
	public SetupAccountRequest(String bankCode, String countryCode, String loginName, String pin, List<String> sync_tasks)	{
		this.bank_code = bankCode;
		this.country = countryCode;
		this.credentials = new ArrayList<String>();
		this.credentials.add(loginName);
		this.credentials.add(pin);
		this.sync_tasks = sync_tasks;
	}
	
	/**
	 * 
	 * @param bankCode
	 * @param countryCode
	 * @param credentials
	 * @param sync_tasks
	 */
	public SetupAccountRequest(String bankCode, String countryCode, List<String> credentials, List<String> sync_tasks)	{
		this.bank_code = bankCode;
		this.country = countryCode;
		this.credentials = credentials;
		this.sync_tasks = sync_tasks;
	}
	
	/**
	 * 
	 * @param bank_code
	 * @param country
	 * @param credentials
	 * @param sync_tasks
	 * @param save_pin
	 * @param disable_first_sync
	 */
	public SetupAccountRequest(String bank_code, String country,
			List<String> credentials, List<String> sync_tasks,
			boolean save_pin, boolean disable_first_sync) {
		super();
		this.bank_code = bank_code;
		this.country = country;
		this.credentials = credentials;
		this.sync_tasks = sync_tasks;
		this.save_pin = save_pin;
		this.disable_first_sync = disable_first_sync;
	}
	
	/**
	 * 
	 * @param bank_code
	 * @param country
	 * @param credentials
	 * @param sync_tasks
	 * @param save_pin
	 * @param disable_first_sync
	 * @param redirect_uri
	 */
	public SetupAccountRequest(String bank_code, String country,
			List<String> credentials, List<String> sync_tasks,
			boolean save_pin, boolean disable_first_sync, String redirect_uri) {
		super();
		this.bank_code = bank_code;
		this.country = country;
		this.credentials = credentials;
		this.sync_tasks = sync_tasks;
		this.save_pin = save_pin;
		this.disable_first_sync = disable_first_sync;
		this.redirect_uri = redirect_uri;
	}
	
	
	@Deprecated
	public SetupAccountRequest(String bankCode, String countryCode, String loginName, String pin)	{
		this.bank_code = bankCode;
		this.country = countryCode;
		this.credentials = new ArrayList<String>();
		this.credentials.add(loginName);
		this.credentials.add(pin);
	}
	
	@Deprecated
	public SetupAccountRequest(String bankCode, String countryCode, List<String> credentials)	{
		this.bank_code = bankCode;
		this.country = countryCode;
		this.credentials = credentials;
	}

	public String getBankCode() {
		return bank_code;
	}

	public void setBankCode(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<String> credentials) {
		this.credentials = credentials;
	}

	public List<String> getSyncTasks() {
		return sync_tasks;
	}

	public void setSyncTasks(List<String> sync_tasks) {
		this.sync_tasks = sync_tasks;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public boolean getSavePin() {
		return save_pin;
	}

	public void setSavePin(boolean save_pin) {
		this.save_pin = save_pin;
	}

	public boolean getDisableFirstSync() {
		return disable_first_sync;
	}

	public void setDisableFirstSync(boolean disable_first_sync) {
		this.disable_first_sync = disable_first_sync;
	}

	public String getRedirectUri() {
		return redirect_uri;
	}

	public void setRedirectUri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
}
