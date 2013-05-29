package me.figo.models;

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
     * Notification messages will be sent to this URL
     */
    public String getNotifyURI() {
    	return notify_uri;
    }
    
    /***
     * State similar to sync and login process. It will passed as POST payload for webhooks
     */
    public String getState() {
    	return state;
    }
}
