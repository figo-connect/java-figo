package me.figo.internal;

import com.google.gson.annotations.Expose;

/**
 * Helper type for the return value of /rest/sync or /rest/accounts/../payments/../submit
 */
public class TaskTokenResponse {
    @Expose
    public String task_token;
    
    public String getTaskToken() {
	return task_token;
	}
}
