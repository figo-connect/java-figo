package me.figo.models;

import com.google.gson.annotations.Expose;

public class ApiError {

    @Expose
    private String name;

    @Expose
    private String message;

    @Expose
    private String description;

    @Expose
    private int code;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
