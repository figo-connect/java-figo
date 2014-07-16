package me.figo.internal;

/**
 * Helper type for OAuth token API
 */
public class TokenRequest {
    public String refresh_token = null;
    public String code          = null;
    public String redirect_uri  = null;
    public String grant_type    = null;

    public TokenRequest(String refresh_token, String code, String redirect_uri, String grant_type) {
        this.refresh_token = refresh_token;
        this.code = code;
        this.redirect_uri = redirect_uri;
        this.grant_type = grant_type;
    }
}
