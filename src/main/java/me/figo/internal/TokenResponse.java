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
    public Integer expires;
}
