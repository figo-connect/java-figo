package me.figo.models;

import com.google.gson.annotations.Expose;

/**
 * Object representing a Bank
 * 
 * @author Stefan Richter
 */
public class Bank {
    /**
     * Internal ID of the bank
     */
    @Expose(serialize = false)
    private String bank_id;

    /**
     * SEPA direct debit creditor ID
     */
    @Expose
    private String sepa_creditor_id;

    /**
     * This flag indicates whether the user has chosen to save the PIN on the figo Connect server
     */
    @Expose(serialize = false)
    private boolean save_pin;

    public Bank() {
    }

    /**
     * @return the internal ID of the bank
     */
    public String getBankId() {
        return bank_id;
    }

    /**
     * @return the SEPA direct debit creditor ID
     */
    public String getSepaCreditorId() {
        return sepa_creditor_id;
    }

    /**
     * @param sepa_creditor_id
     *            the SEPA direct debit creditor ID to set
     */
    public void setSepaCreditorId(String sepa_creditor_id) {
        this.sepa_creditor_id = sepa_creditor_id;
    }

    /**
     * @return whether the user has chosen to save the PIN on the figo Connect server
     */
    public boolean isPinSaved() {
        return save_pin;
    }
}
