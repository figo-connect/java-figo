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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * Object representing one bank transaction on a certain bank account of the user
 *
 * @author Stefan Richter
 */
public class Transaction {

    /**
     * Internal figo Connect transaction ID
     */
    @Expose(serialize = false)
    private String  transaction_id;

    /**
     * Internal figo Connect account ID
     */
    @Expose(serialize = false)
    private String  account_id;

    /**
     * Name of originator or recipient
     */
    @Expose(serialize = false)
    private String  name;

    /**
     * Account number of originator or recipient
     */
    @Expose(serialize = false)
    private String  account_number;

    /**
     * Bank code of originator or recipient
     */
    @Expose(serialize = false)
    private String  bank_code;

    /**
     * Bank name of originator or recipient
     */
    @Expose(serialize = false)
    private String  bank_name;

    /**
     * Transaction amount
     */
    @Expose(serialize = false)
    private BigDecimal   amount;

    /**
     * Three-character currency code
     */
    @Expose(serialize = false)
    private String  currency;

    /**
     * Booking date
     */
    @Expose(serialize = false)
    private Date    booking_date;

    /**
     * Value date
     */
    @Expose(serialize = false)
    private Date    value_date;

    /**
     * Purpose text
     */
    @Expose(serialize = false)
    private String  purpose;

    /**
     * Transaction type: Transfer, Standing order, Direct debit, Salary or rent, Electronic cash, GeldKarte, ATM, Charges or interest or Unknown
     */
    @Expose(serialize = false)
    private String  type;

    /**
     * Booking text
     */
    @Expose(serialize = false)
    private String  booking_text;

    /**
     * This flag indicates whether the transaction is booked or pending
     */
    @Expose(serialize = false)
    private boolean booked;

    @Expose(serialize = false)
    private boolean visited;

    @Expose(serialize = false)
    private String transaction_code;

    @Expose(serialize = false)
    private AdditionalTransactionInfo additional_info;

    @Expose
    private List<Category> categories;

	@Expose(serialize = false)
	private String iban;

	@Expose(serialize = false)
	private String bic;

	@Expose(serialize = false)
	private String booking_key;

	@Expose(serialize = false)
	private String creditor_id;

	@Expose(serialize = false)
	private String mandate_reference;

	@Expose(serialize = false)
	private String sepa_purpose_code;

	@Expose(serialize = false)
	private String sepa_remittance_info;

	@Expose(serialize = false)
	private String text_key_addition;

	@Expose(serialize = false)
	private String end_to_end_reference;

	@Expose(serialize = false)
	private String customer_reference;

	@Expose(serialize = false)
	private String prima_nota_number;

    public Transaction() {
    }

    /**
     * @return the internal figo Connect transaction ID
     */
    public String getTransactionId() {
        return transaction_id;
    }

    /**
     * @return the internal figo Connect account ID
     */
    public String getAccountId() {
        return account_id;
    }

    /**
     * @return the name of originator or recipient
     */
    public String getName() {
        return name;
    }

    /**
     * @return the account number of originator or recipient
     */
    public String getAccountNumber() {
        return account_number;
    }

    /**
     * @return the bank code of originator or recipient
     */
    public String getBankCode() {
        return bank_code;
    }

    /**
     * @return the bank name of originator or recipient
     */
    public String getBankName() {
        return bank_name;
    }

    /**
     * @return the transaction amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @return the three-character currency code
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return the booking date
     */
    public Date getBookingDate() {
        return booking_date;
    }

    /**
     * @return the value date
     */
    public Date getValueDate() {
        return value_date;
    }

    /**
     * @return the purpose text
     */
    public String getPurposeText() {
        return purpose;
    }

    /**
     * @return the transaction type: Transfer, Standing order, Direct debit, Salary or rent, Electronic cash, GeldKarte, ATM, Charges or interest or Unknown
     */
    public String getType() {
        return type;
    }

    /**
     * @return the booking text
     */
    public String getBookingText() {
        return booking_text;
    }

    /**
     * @return whether the transaction is booked or pending
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * @return whether the transaction has been visited or not
     */
    public boolean isVisited()	{
    	return visited;
    }

    public String getTransactionCode() {
        return transaction_code;
    }

    public AdditionalTransactionInfo getAdditionalInfo() {
        return additional_info;
    }

    public List<Category> getCategories()   {
        return categories;
    }

    /**
     * Helper type to represent the actual answer from the figo API
     */
    public static class TransactionsResponse {
        /**
         * List of transactions asked for
         */
        @Expose
        private List<Transaction>     transactions;

        /**
         * Synchronization status between figo and bank servers
         */
        @Expose
        private SynchronizationStatus status;

        public TransactionsResponse() {
        }

        /**
         * @return the list of transactions asked for
         */
        public List<Transaction> getTransactions() {
            return transactions;
        }

        /**
         * @return the synchronization status between figo and bank servers
         */
        public SynchronizationStatus getStatus() {
            return status;
        }
    }

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getBooking_key() {
		return booking_key;
	}

	public void setBooking_key(String booking_key) {
		this.booking_key = booking_key;
	}

	public String getCreditor_id() {
		return creditor_id;
	}

	public void setCreditor_id(String creditor_id) {
		this.creditor_id = creditor_id;
	}

	public String getMandate_reference() {
		return mandate_reference;
	}

	public void setMandate_reference(String mandate_reference) {
		this.mandate_reference = mandate_reference;
	}

	public String getSepa_purpose_code() {
		return sepa_purpose_code;
	}

	public void setSepa_purpose_code(String sepa_purpose_code) {
		this.sepa_purpose_code = sepa_purpose_code;
	}

	public String getSepa_remittance_info() {
		return sepa_remittance_info;
	}

	public void setSepa_remittance_info(String sepa_remittance_info) {
		this.sepa_remittance_info = sepa_remittance_info;
	}

	public String getText_key_addition() {
		return text_key_addition;
	}

	public void setText_key_addition(String text_key_addition) {
		this.text_key_addition = text_key_addition;
	}

	public String getEnd_to_end_reference() {
		return end_to_end_reference;
	}

	public void setEnd_to_end_reference(String end_to_end_reference) {
		this.end_to_end_reference = end_to_end_reference;
	}

	public String getCustomer_reference() {
		return customer_reference;
	}

	public void setCustomer_reference(String customer_reference) {
		this.customer_reference = customer_reference;
	}

	public String getPrima_nota_number() {
		return prima_nota_number;
	}

	public void setPrima_nota_number(String prima_nota_number) {
		this.prima_nota_number = prima_nota_number;
	}
}
