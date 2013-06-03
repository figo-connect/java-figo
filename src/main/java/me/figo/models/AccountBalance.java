package me.figo.models;

import java.util.Date;

/***
 * Object representing the balance of a certain bank account of the user
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 */
public class AccountBalance {
    /***
     * Account balance or None if the balance is not yet known
     */
	private float balance;
    
	/***
	 * Bank server timestamp of balance or None if the balance is not yet known.
	 */
    private Date balance_date;
    
    /***
     * Credit line
     */
    private float credit_line;
    
    /***
     * User-defined spending limit
     */
    private float monthly_spending_limit;
    
    public AccountBalance() {}
    
    /***
     * Account balance or null if the balance is not yet known
     */
    public float getBalance() {
    	return balance;
    }
    
    /***
     * Bank server timestamp of balance or null if the balance is not yet known.
     */
    public Date getBalanceDate() {
    	return balance_date;
    }
    
    /***
     * Credit line
     */
    public float getCreditLine() {
    	return credit_line;
    }
    
    /***
     * User-defined spending limit
     */
    public float getMonthlySpendingLimit() {
    	return monthly_spending_limit;
    }
}
