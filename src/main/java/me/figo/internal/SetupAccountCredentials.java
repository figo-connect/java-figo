package me.figo.internal;

import java.util.List;

public class SetupAccountCredentials {

    private List<String> credentials;
    private String encryptedCredentials;

    public SetupAccountCredentials(String encryptedCredentials) {
        this.encryptedCredentials = encryptedCredentials;
    }

    public SetupAccountCredentials(List<String> credentials) {

        this.credentials = credentials;
    }

    public List<String> getCredentials() {
        return credentials;
    }

    public String getEncryptedCredentials() {
        return encryptedCredentials;
    }
}
