//
// Copyright (c) 2013 figo GmbH
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

package me.figo.models;

import java.util.HashMap;
import java.util.List;

/**
 * Object representing one bank account of the user, independent of the exact account type
 * 
 * @author Stefan Richter <stefan.richter@figo.me>
 */
public class Account {

    /**
     * Internal figo Connect account ID
     */
    private String                  account_id;

    /**
     * Internal figo Connect bank ID
     */
    private String                  bank_id;

    /**
     * Account name
     */
    private String                  name;

    /**
     * Account owner
     */
    private String                  owner;

    /**
     * This flag indicates whether the account will be automatically synchronized
     */
    private boolean                 auto_sync;

    /**
     * Account number
     */
    private String                  account_number;

    /**
     * Bank code
     */
    private String                  bank_code;

    /**
     * Bank name
     */
    private String                  bank_name;

    /**
     * Three-character currency code
     */
    private String                  currency;

    /**
     * IBAN
     */
    private String                  iban;

    /**
     * BIC
     */
    private String                  bic;

    /**
     * Account type: Giro account, Savings account, Credit card, Loan account, PayPal, Cash book or Unknown
     */
    private String                  type;

    /**
     * Account icon URL
     */
    private String                  icon;
    
    /**
     * Account balance details
     */
    private AccountBalance          balance;

    /**
     * Account icon in other resolutions
     */
    private HashMap<String, String> addtional_icons;

    public Account() {
    }

    /**
     * @return the internal figo Connect account ID
     */
    public String getAccountId() {
        return account_id;
    }

    /**
     * @return the internal figo Connect bank ID
     */
    public String getBankId() {
        return bank_id;
    }

    /**
     * @return the account name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the account name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the account owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the account owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return whether the account will be automatically synchronized
     */
    public boolean isAutoSync() {
        return auto_sync;
    }

    /**
     * @param auto_sync
     *            the auto synchronization setting to set
     */
    public void setAutoSync(boolean auto_sync) {
        this.auto_sync = auto_sync;
    }

    /**
     * @return the account number
     */
    public String getAccountNumber() {
        return account_number;
    }

    /**
     * @return the bank code
     */
    public String getBankCode() {
        return bank_code;
    }

    /**
     * @return the bank name
     */
    public String getBankName() {
        return bank_name;
    }

    /**
     * @return the three-character currency code
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return the IBAN
     */
    public String getIBAN() {
        return iban;
    }

    /**
     * @return the BIC
     */
    public String getBIC() {
        return bic;
    }

    /**
     * @return the account type: Giro account, Savings account, Credit card, Loan account, PayPal or Unknown
     */
    public String getType() {
        return type;
    }
    
    /**
     * @return balance details
     */
    public AccountBalance getBalance() {
        return balance;
    }

    /**
     * @return the account icon URL
     */
    public String getIconUrl() {
        return icon;
    }

    /**
     * @return the account icon in other resolutions
     */
    public HashMap<String, String> getAddtionalIcons() {
        return addtional_icons;
    }

    /**
     * Helper type to match actual response from figo API
     */
    public class AccountsResponse {
        /**
         * List of accounts asked for
         */
        private List<Account> accounts;

        public AccountsResponse() {
        }

        /**
         * List of accounts asked for
         */
        public List<Account> getAccounts() {
            return accounts;
        }
    }
}
