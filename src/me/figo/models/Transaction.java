package me.figo.models;

import java.util.Date;

/***
 * Object representing one bank transaction on a certain bank account of the user
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 */
public class Transaction {
    
	/***
	 * Internal figo Connect transaction ID
	 */
    private String transaction_id;
    
    /***
     * Internal figo Connect account ID
     */
    private String account_id;
    
    /***
     * Name of originator or recipient
     */
    private String name;
    
    /***
     * Account number of originator or recipient
     */
    private String account_number;
    
    /***
     * Bank code of originator or recipient
     */
    private String bank_code;
    
    /***
     * Bank name of originator or recipient
     */
    private String bank_name;
    
    /***
     * Transaction amount
     */
    private float amount;
    
    /***
     * Three-character currency code
     */
    private String currency;
    
    /***
     * Booking date
     */
    private Date booking_date;
    
    /***
     * Value date
     */
    private Date value_date;
    
    /***
     * Purpose text
     */
    private String purpose;
    
    /***
     * Transaction type: Transfer, Standing order, Direct debit, Salary or rent, Electronic cash, GeldKarte, ATM, Charges or interest or Unknown
     */
    private String type;
    
    /***
     * Booking text
     */
    private String booking_text;
    
    /***
     * This flag indicates whether the transaction is booked or pending
     */
    private boolean booked;

    public Transaction() {}
    
    /***
     * Internal figo Connect transaction ID
     */
    public String getTransactionId() {
    	return transaction_id;
    }
    
    /***
     * Internal figo Connect account ID
     */
    public String getAccountId() {
    	return account_id;
    }

    /***
     * Name of originator or recipient
     */
    public String getName() {
    	return name;
    }

    /***
     * Account number of originator or recipient
     */
    public String getAccountNumber() {
    	return account_number;
    }
    
    /***
     * Bank code of originator or recipient
     */
    public String getBankCode() {
    	return bank_code;
    }
    
    /***
     * Bank name of originator or recipient
     */
    public String getBankName() {
    	return bank_name;
    }
    
    /***
     * Transaction amount
     */
    public float getAmount() {
    	return amount;
    }

    /***
     * Three-character currency code
     */
    public String getCurrency() {
    	return currency;
    }
    
    /***
     * Booking date
     */
    public Date getBookingDate() {
    	return booking_date;
    }
    
    /***
     * Value date
     */
    public Date getValueDate() {
    	return value_date;
    }
    
    /***
     * Purpose text
     */
    public String getPurposeText() {
    	return purpose;
    }
    
    /***
     * Transaction type: Transfer, Standing order, Direct debit, Salary or rent, Electronic cash, GeldKarte, ATM, Charges or interest or Unknown
     */
    public String getType() {
    	return type;
    }

    /***
     * Booking text
     */
    public String getBookingText() {
    	return booking_text;
    }
    
    /***
     * This flag indicates whether the transaction is booked or pending
     */
    public boolean isBooked() {
    	return booked;
    }
}

