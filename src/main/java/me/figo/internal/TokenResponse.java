package me.figo.internal;

import com.google.gson.annotations.Expose;

/**
 * Helper type for OAuth token API
 */
public class TokenResponse {
    @Expose
    public String  access_token;

    @Expose
    public String  refresh_token;    

	@Expose
    public Integer expires_in;
	
	public String getAccessToken() {
		return access_token;
	}

	public String getRefreshToken() {
		return refresh_token;
	}

	public Integer getExpiresIn() {
		return expires_in;
	}
}
