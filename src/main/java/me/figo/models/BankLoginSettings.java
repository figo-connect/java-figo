package me.figo.models;

import com.google.gson.annotations.Expose;

public class BankLoginSettings extends LoginSettings    {

    public BankLoginSettings() {
    }

    @Expose
    private String bank_name;

    public String getBankName() {
        return bank_name;
    }

    public void setBankName(String bankName) {
        this.bank_name = bankName;
    }
}
