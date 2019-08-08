package me.figo.internal;

import com.google.gson.annotations.Expose;

public class SyncChallengeRequest {

	@Expose
	public String method_id;

	public String getMethodId() {
		return method_id;
	}

	public SyncChallengeRequest(String methodId) {
		super();
		this.method_id = methodId;
	}

	public void setMethodId(String methodId) {
		this.method_id = methodId;
	}
}
