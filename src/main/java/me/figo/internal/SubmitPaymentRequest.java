package me.figo.internal;

/**
 * Helper type for the request value of /rest/accounts/../payments/../submit
 */
public class SubmitPaymentRequest {
    public String tan_scheme_id;
    public String state;
    public String redirect_uri;
    
    public SubmitPaymentRequest(String tanSchemeId, String state, String redirectUri) {
        this.tan_scheme_id = tanSchemeId;
        this.state = state;
        this.redirect_uri = redirectUri;
    }
}
