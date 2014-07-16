package me.figo.internal;

/**
 * Helper type for OAuth token API
 */
public class TokenResponse {
    public String  access_token  = null;
    public String  refresh_token = null;
    public Integer expires       = 0;
}
