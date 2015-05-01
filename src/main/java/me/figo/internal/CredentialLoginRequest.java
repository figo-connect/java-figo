package me.figo.internal;

import com.google.gson.annotations.Expose;

/**
 * Helper type for credential login
 */
public class CredentialLoginRequest {
	@Expose
    private String grant_type = "password";

    @Expose
    private String username;
    
    @Expose
    private String password;
    
    public CredentialLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

	public String getGrantType() {
		return grant_type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
