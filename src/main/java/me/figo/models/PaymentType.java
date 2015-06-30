package me.figo.models;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class PaymentType {

	@Expose
	private List<String> allowed_recipients;
	
	@Expose
	private int max_purpose_length;
	
	@Expose
	private List<String> supported_text_keys;
	
	@Expose
	private Date min_scheduled_date;
	
	@Expose
	private Date max_scheduled_date;
	
	@Expose
	private List<String> supported_file_formats;

	public List<String> getAllowedRecipients() {
		return allowed_recipients;
	}

	public int getMaxPurposeLength() {
		return max_purpose_length;
	}

	public List<String> getSupportedTextKeys() {
		return supported_text_keys;
	}

	public Date getMinScheduledDate() {
		return min_scheduled_date;
	}

	public Date getMaxScheduledDate() {
		return max_scheduled_date;
	}

	public List<String> getSupportedFileFormats() {
		return supported_file_formats;
	}
	
	
	
}
