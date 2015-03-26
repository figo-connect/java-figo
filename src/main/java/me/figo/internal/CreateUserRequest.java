package me.figo.internal;

import com.google.gson.annotations.Expose;

/**
 * Helper type for user creation
 */
public class CreateUserRequest {
    @Expose
    private String  name;

    @Expose
    private String  email;
    
    @Expose
    private String  password;
    
    @Expose
    private String  language;

    public CreateUserRequest(String name, String email, String password, String language) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.language = language;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
    
    
    
}