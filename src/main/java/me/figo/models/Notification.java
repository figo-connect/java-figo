package me.figo.models;

import java.util.List;

/***
 * Object representing a configured notification, e.g a webhook or email hook
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 */
public class Notification {

	/***
	 * Internal figo Connect notification ID from the notification registration response
	 */
    private String notification_id;
    
    /***
     * Notification key: see http://developer.figo.me/#notification_keys
     */
    private String observe_key;
    
    /***
     * Notification messages will be sent to this URL
     */
    private String notify_uri;
    
    /***
     * State similar to sync and login process. It will passed as POST payload for webhooks
     */
    private String state;
    
    public Notification() {}
    
    public Notification(String observe_key, String notify_uri, String state) {
    	this.observe_key = observe_key;
    	this.notify_uri = notify_uri;
    	this.state = state;
    }
    
    /***
     * Internal figo Connect notification ID from the notification registration response
     */
    public String getNotificationId() {
    	return notification_id;
    }
    
    /***
     * Notification key: see http://developer.figo.me/#notification_keys
     */
    public String getObserveKey() {
    	return observe_key;
    }

    /***
     * Notification key: see http://developer.figo.me/#notification_keys
     */
    public void setObserveKey(String key) {
    	observe_key = key;
    }
    
    /***
     * Notification messages will be sent to this URL
     */
    public String getNotifyURI() {
    	return notify_uri;
    }

    /***
     * Notification messages will be sent to this URL
     */
    public void setNotifyURI(String uri) {
    	notify_uri = uri;
    }
    
    /***
     * State similar to sync and login process. It will passed as POST payload for webhooks
     */
    public String getState() {
    	return state;
    }

    /***
     * State similar to sync and login process. It will passed as POST payload for webhooks
     */
    public void setState(String state) {
    	this.state = state;
    }
    
    /***
     * Helper type to match actual response from figo API
     */
    public class NotificationsResponse {
    	/***
    	 * List of notifications asked for
    	 */
    	private List<Notification> notifications;
    	
    	public NotificationsResponse() {}

    	/***
    	 * List of notifications asked for
    	 */
    	public List<Notification> getNotifications() {
    		return notifications;
    	}
    }
}
