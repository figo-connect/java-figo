package me.figo.models;

import java.util.Date;

/***
 * Represents the status of the synchonisation between figo and the bank servers
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 *
 */
public class SynchronizationStatus {
	/***
	 * Internal figo status code
	 */
	private Integer code;
	
	/***
	 * Human-readable error message. 
	 */
	private String message;
	
	/***
	 * Timestamp of last synchronization
	 */
	private Date sync_timestamp;
	
	/***
	 * Timestamp of last successful synchronization
	 */
	private Date success_timestamp;


	public SynchronizationStatus() {}
	
	/***
	 * Internal figo status code
	 */
	public Integer getCode() {
		return code;
	}
	
	/***
	 * Human-readable error message
	 */
	public String getMessage() {
		return message;
	}
	
	/***
	 * Timestamp of last synchronization
	 */
	public Date getSyncTimestamp() {
		return sync_timestamp;
	}
	
	/***
	 * Timestamp of last successful synchronization
	 * @return
	 */
	public Date getSuccessTimestamp() {
		return success_timestamp;
	}
}
