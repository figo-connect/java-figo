package me.figo.models;

import com.google.gson.annotations.Expose;

public class ServiceLoginSettings extends LoginSettings {

    public ServiceLoginSettings() {
    }

    @Expose
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
