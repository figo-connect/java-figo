package me.figo.models;

import com.google.gson.annotations.Expose;

public class TanScheme {

	@Expose
	private String tan_scheme_id;
	
	@Expose
	private String name;
	
	@Expose
	private String medium_name;

    public String getTan_scheme_id() {
        return tan_scheme_id;
    }

    public void setTan_scheme_id(String tan_scheme_id) {
        this.tan_scheme_id = tan_scheme_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedium_name() {
        return medium_name;
    }

    public void setMedium_name(String medium_name) {
        this.medium_name = medium_name;
    }
}
