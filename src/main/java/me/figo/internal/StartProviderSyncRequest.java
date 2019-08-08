package me.figo.internal;

import com.google.gson.annotations.Expose;

/**
 * Helper type for the call to /rest/sync
 */
public class StartProviderSyncRequest {
    
	public StartProviderSyncRequest(String state, String redirect_uri, boolean disable_notifications,
			boolean save_credentials, Credentials credentials) {
		super();
		this.state = state;
		this.redirect_uri = redirect_uri;
		this.disable_notifications = disable_notifications;
		this.save_credentials = save_credentials;
		this.credentials = credentials;
	}

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
    
	@Expose
	public boolean save_credentials;

	@Expose
	public Credentials credentials;

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public boolean isDisable_notifications() {
		return disable_notifications;
	}

	public void setDisable_notifications(boolean disable_notifications) {
		this.disable_notifications = disable_notifications;
	}

	public boolean isSave_credentials() {
		return save_credentials;
	}

	public void setSave_credentials(boolean save_credentials) {
		this.save_credentials = save_credentials;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirectUri() {
        return redirect_uri;
    }

    public void setRedirectUri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public boolean isDisableNotifications() {
        return disable_notifications;
    }

    public void setDisableNotifications(boolean disable_notifications) {
        this.disable_notifications = disable_notifications;
    }
}
