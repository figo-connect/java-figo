package me.figo.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Object representing a Payment
 * 
 * @author Stefan Richter
 */
public class Payment {
    /**
     * Internal figo Connect payment ID
     */
    @Expose(serialize = false)
    private String payment_id;

    /**
     * Internal figo Connect account ID
     */
    @Expose(serialize = false)
    private String account_id;

    /**
     * Payment type
     */
    @Expose
    private String type;

    /**
     * Name of creditor or debtor
     */
    @Expose
    private String name;

    /**
     * Account number of creditor or debtor
     */
    @Expose
    private String account_number;

    /**
     * Bank code of creditor or debtor
     */
    @Expose
    private String bank_code;

    /**
     * Bank name of creditor or debtor
     */
    @Expose(serialize = false)
    private String bank_name;

    /**
     * Icon of creditor or debtor bank
     */
    @Expose(serialize = false)
    private String bank_icon;

    /**
     * Icon of the creditor or debtor bank in other resolutions
     */
    @Expose(serialize = false)
    private HashMap<String, String> bank_additional_icons;

    /**
     * Order amount
     */
    @Expose
    private BigDecimal amount;

    /**
     * Three-character currency code
     */
    @Expose
    private String currency;

    /**
     * Purpose text
     */
    @Expose
    private String purpose;

    /**
     * Timestamp of submission to the bank server
     */
    @Expose(serialize = false)
    private Date submission_timestamp;

    /**
     * Internal creation timestamp on the figo Connect server
     */
    @Expose(serialize = false)
    private Date creation_timestamp;

    /**
     * Internal modification timestamp on the figo Connect server
     */
    @Expose(serialize = false)
    private Date modification_timestamp;

    /**
     * Transaction ID. This field is only set if the payment has been matched to a transaction.
     */
    @Expose
    private String transaction_id;

    public Payment() {
    }

    public Payment(String type, String account_id, String account_number, String bank_code, String name, String purpose, BigDecimal amount) {
        this.type = type;
        this.account_id = account_id;
        this.account_number = account_number;
        this.bank_code = bank_code;
        this.name = name;
        this.purpose = purpose;
        this.currency = "EUR";
        this.amount = amount;
    }

    public Payment(String type, String account_id, String account_number, String bank_code, String name, String purpose, String currency, BigDecimal amount) {
        this.type = type;
        this.account_id = account_id;
        this.account_number = account_number;
        this.bank_code = bank_code;
        this.name = name;
        this.purpose = purpose;
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * @return the internal figo Connect payment ID
     */
    public String getPaymentId() {
        return payment_id;
    }

    /**
     * @return the internal figo Connect account ID
     */
    public String getAccountId() {
        return account_id;
    }

    /**
     * @param account_id
     *            the internal figo Connect account ID to set
     */
    public void setAccountId(String account_id) {
        this.account_id = account_id;
    }

    /**
     * @return the payment type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the payment type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the name of creditor or debtor
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name of creditor or debtor to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the account number of creditor or debtor
     */
    public String getAccountNumber() {
        return account_number;
    }

    /**
     * @param account_number
     *            the account number of creditor or debtor to set
     */
    public void setAccountNumber(String account_number) {
        this.account_number = account_number;
    }

    /**
     * @return the bank code of creditor or debtor
     */
    public String getBankCode() {
        return bank_code;
    }

    /**
     * @param bank_code
     *            the bank code of creditor or debtor to set
     */
    public void setBankCode(String bank_code) {
        this.bank_code = bank_code;
    }

    /**
     * @return the bank name of creditor or debtor
     */
    public String getBankName() {
        return bank_name;
    }

    /**
     * @return the icon of creditor or debtor bank
     */
    public String getBankIcon() {
        return bank_icon;
    }

    /**
     * @return the icon of the creditor or debtor bank in other resolutions
     */
    public HashMap<String, String> getBankAdditionalIcons() {
        return bank_additional_icons;
    }

    /**
     * @return the order amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            the order amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the three-character currency code
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency
     *            the three-character currency code to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the purpose text
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose
     *            the purpose text to set
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * @return the timestamp of submission to the bank server
     */
    public Date getSubmissionTimestamp() {
        return submission_timestamp;
    }

    /**
     * @return the internal creation timestamp on the figo Connect server
     */
    public Date getCreationTimestamp() {
        return creation_timestamp;
    }

    /**
     * @return the internal modification timestamp on the figo Connect server
     */
    public Date getModificationTimestamp() {
        return modification_timestamp;
    }

    /**
     * @return the transaction ID
     */
    public String getTransactionId() {
        return transaction_id;
    }

    /**
     * @param transaction_id
     *            the transaction ID to set
     */
    public void setTransactionId(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    /***
     * Helper type to represent the actual answer from the figo API
     */
    public static class PaymentsResponse {
        /***
         * List of transactions asked for
         */
        @Expose
        private List<Payment> payments;

        public PaymentsResponse() {
        }

        /***
         * @return the list of transactions asked for
         */
        public List<Payment> getPayments() {
            return payments;
        }
    }
}
