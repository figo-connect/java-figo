package me.figo.internal;

import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Helper type for the call to /rest/sync
 */
public class SyncTokenRequest {
    
	/**
     * State to return when coming back to the calling application after the synchronization finished
     */
    @Expose
    public String state;

    /**
     * URL to redirect to when the synchronization finished
     */
    @Expose
    public String redirect_uri;
    
    /**
     * Indicates whether notifications should be sent to your application
     */
    @Expose
    public boolean disable_notifications;
    
    /**
     * Sync accounts only which have not been synchronized within the specified number of minutes
     */
    @Expose
    public int if_not_synced_since;
    
    /**
     * Should the sync process continue on errors without asking for response 
     */
    @Expose
    public boolean auto_continue;
    
    /**
     * Tasks to sync
     */
    @Expose
    public List<String> sync_tasks;
    
    /**
     * Accounts to sync
     */
    @Expose
    public List<String> account_ids;
    
    
    /**
     * 
     * @param state
     * @param redirect_uri
     */
    public SyncTokenRequest(String state, String redirect_uri) {
        this.state = state;
        this.redirect_uri = redirect_uri;
    }
    
    /**
     * 
     * @param state
     * @param redirect_uri
     * @param sync_tasks
     */
    public SyncTokenRequest(String state, String redirect_uri, List<String> sync_tasks) {
        this.state = state;
        this.redirect_uri = redirect_uri;
        this.sync_tasks = sync_tasks;
    }
    
    /**
     * 
     * @param state
     * @param redirect_uri
     * @param sync_tasks
     * @param account_ids
     */
    public SyncTokenRequest(String state, String redirect_uri, List<String> sync_tasks, List<String> account_ids) {
        this.state = state;
        this.redirect_uri = redirect_uri;
        this.sync_tasks = sync_tasks;
        this.account_ids = account_ids;
    }

    /**
     * 
     * @param state
     * @param redirect_uri
     * @param sync_tasks
     * @param account_ids
     * @param disable_notifications
     * @param if_not_synced_since
     * @param auto_continue
     */
	public SyncTokenRequest(String state, String redirect_uri,
			List<String> sync_tasks, List<String> account_ids, 
			boolean disable_notifications,  int if_not_synced_since, 
			boolean auto_continue) {
		this.state = state;
		this.redirect_uri = redirect_uri;
		this.disable_notifications = disable_notifications;
		this.if_not_synced_since = if_not_synced_since;
		this.auto_continue = auto_continue;
		this.sync_tasks = sync_tasks;
		this.account_ids = account_ids;
	}
    
    
}
