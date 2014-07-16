package me.figo.internal;

/**
 * Helper type for the call to /rest/sync
 */
public class SyncTokenRequest {
    /**
     * State to return when coming back to the calling application after the synchronization finished
     */
    public String state;

    /**
     * URL to redirect to when the synchronization finished
     */
    public String redirect_uri;

    public SyncTokenRequest(String state, String redirect_uri) {
        this.state = state;
        this.redirect_uri = redirect_uri;
    }
}