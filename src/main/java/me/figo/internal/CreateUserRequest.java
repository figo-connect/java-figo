package me.figo.internal;

/**
 * Helper type for user creation
 */
public class CreateUserRequest {
    public String  name            = null;
    public String  email           = null;
    public String  password        = null;
    public String  language        = null;
    public boolean send_newsletter = true;

    public CreateUserRequest(String name, String email, String password, String language, boolean send_newsletter) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.language = language;
        this.send_newsletter = send_newsletter;
    }
}