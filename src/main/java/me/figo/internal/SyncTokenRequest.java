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
     * Tasks to sync
     */
    public List<String> sync_tasks;

    public SyncTokenRequest(String state, String redirect_uri) {
        this.state = state;
        this.redirect_uri = redirect_uri;
    }
    
    public SyncTokenRequest(String state, String redirect_uri, List<String> sync_tasks) {
        this.state = state;
        this.redirect_uri = redirect_uri;
        this.sync_tasks = sync_tasks;
    }
}