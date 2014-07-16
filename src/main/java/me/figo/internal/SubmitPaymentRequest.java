package me.figo.internal;

import com.google.gson.annotations.Expose;

/**
 * Helper type for the request value of /rest/accounts/../payments/../submit
 */
public class SubmitPaymentRequest {
    @Expose
    public String tan_scheme_id;
    
    @Expose
    public String state;
    
    @Expose
    public String redirect_uri;
    
    public SubmitPaymentRequest(String tanSchemeId, String state, String redirectUri) {
        this.tan_scheme_id = tanSchemeId;
        this.state = state;
        this.redirect_uri = redirectUri;
    }
}
