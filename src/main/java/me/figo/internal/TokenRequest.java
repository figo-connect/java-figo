package me.figo.internal;

import com.google.gson.annotations.Expose;

/**
 * Helper type for OAuth token API
 */
public class TokenRequest {
    @Expose
    public String refresh_token;

    @Expose
    public String code;

    @Expose
    public String redirect_uri;

    @Expose
    public String grant_type;

    public TokenRequest(String refresh_token, String code, String redirect_uri, String grant_type) {
        this.refresh_token = refresh_token;
        this.code = code;
        this.redirect_uri = redirect_uri;
        this.grant_type = grant_type;
    }
}
