package me.figo.models;


import com.google.gson.annotations.Expose;

public class Category {

    @Expose
    private int parent_id;

    @Expose
    private int id;

    @Expose
    private String name;

    public int getParentId() {
        return parent_id;
    }

    public int getId()   {
        return id;
    }

    public String getName() {
        return name;
    }

}
