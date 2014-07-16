package me.figo.internal;

import com.google.gson.annotations.Expose;

/**
 * Helper type for user creation
 */
public class CreateUserRequest {
    @Expose
    public String  name;

    @Expose
    public String  email;
    
    @Expose
    public String  password;
    
    @Expose
    public String  language;
    
    @Expose
    public boolean send_newsletter = true;

    public CreateUserRequest(String name, String email, String password, String language, boolean send_newsletter) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.language = language;
        this.send_newsletter = send_newsletter;
    }
}