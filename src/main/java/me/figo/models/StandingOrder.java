//
// Copyright (c) 2015 figo GmbH
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

import me.figo.internal.StandingOrderIntervalType;

/**
 * Object representing one bank standing order on a certain bank account of the user
 *
 * @author Joachim Penk
 */
public class StandingOrder {

    /**
     * Internal figo Connect standing order ID
     */
    @Expose(serialize = false)
    private String  standing_order_id;

    /**
     * Internal figo Connect account ID
     */
    @Expose(serialize = false)
    private String  account_id;

    /**
     * Name of recipient
     */
    @Expose(serialize = false)
    private String  name;

    /**
     * Account number of recipient
     */
    @Expose(serialize = false)
    private String  account_number;

    /**
     * Bank code of recipient
     */
    @Expose(serialize = false)
    private String  bank_code;

    /**
     * Bank name of recipient
     */
    @Expose(serialize = false)
    private String  bank_name;

    /**
     * Standing order amount
     */
    @Expose(serialize = false)
    private BigDecimal   amount;

    /**
     * Three-character currency code
     */
    @Expose(serialize = false)
    private String  currency;

    /**
     * Creation date
     */
    @Expose(serialize = false)
    private Date    creation_timestamp;

    /**
     * Purpose text
     */
    @Expose(serialize = false)
    private String  purpose;

    /**
     * Execution Day
     */
    @Expose(serialize = false)
    private Integer  execution_day;

    /**
     * Next Execution Date
     */
    @Expose(serialize = false)
    private Date  next_execution_date;

    /**
     * Execution interval
     */
    @Expose(serialize = false)
    private StandingOrderIntervalType  interval;

	/**
	 * First execution date
	 */
	@Expose(serialize = false)
	private Date first_execution_date;

	/**
	 * Last execution date
	 */
	@Expose(serialize = false)
	private Date last_execution_date;

    public StandingOrder() {
    }

    /**
     * @return the internal figo Connect standing order ID
     */
    public String getStandingOrderId() {
        return standing_order_id;
    }

    /**
     * @return the internal figo Connect account ID
     */
    public String getAccountId() {
        return account_id;
    }

    /**
     * @return the name of recipient
     */
    public String getName() {
        return name;
    }

    /**
     * @return the account number of recipient
     */
    public String getAccountNumber() {
        return account_number;
    }

    /**
     * @return the bank code of recipient
     */
    public String getBankCode() {
        return bank_code;
    }

    /**
     * @return the bank name of recipient
     */
    public String getBankName() {
        return bank_name;
    }

    /**
     * @return the standing order amount
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
     * @return the purpose text
     */
    public String getPurposeText() {
        return purpose;
    }

    /**
     * @return the creation date 
     */
	public Date getCreationTimestamp() {
		return creation_timestamp;
	}


	/**
	 * @return day of the month the standing order is executed
	 */
	public Integer getExecutionDay() {
		return execution_day;
	}

	/**
	 * @return next Date the standing order gets executed
	 */
	public Date getNextExecutionDate() {
		return next_execution_date;
	}

	/**
	 * @return the monthly interval the standing order is executed
	 * 
	 */
	public StandingOrderIntervalType getInterval() {
		return interval;
	}

    public void setStandingOrderId(String standing_order_id) {
        this.standing_order_id = standing_order_id;
    }

    public void setAccountId(String account_id) {
        this.account_id = account_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountNumber(String account_number) {
        this.account_number = account_number;
    }

    public void setBankCode(String bank_code) {
        this.bank_code = bank_code;
    }

    public void setBankName(String bank_name) {
        this.bank_name = bank_name;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCreationTimestamp(Date creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setExecutionDay(Integer execution_day) {
        this.execution_day = execution_day;
    }

    public void setNextExecutionDate(Date next_execution_date) {
        this.next_execution_date = next_execution_date;
    }

    public void setInterval(StandingOrderIntervalType interval) {
        this.interval = interval;
    }

    /**
     * Helper type to represent the actual answer from the figo API
     */
    public static class StandingOrdersResponse {
        /**
         * List of standing orders asked for
         */
        @Expose
        private List<StandingOrder>     standing_orders;

        /**
         * Synchronization status between figo and bank servers
         */
        @Expose
        private SynchronizationStatus status;

        public StandingOrdersResponse() {
        }

        /**
         * @return the list of standing orders asked for
         */
        public List<StandingOrder> getStandingOrders() {
            return standing_orders;
        }

        /**
         * @return the synchronization status between figo and bank servers
         */
        public SynchronizationStatus getStatus() {
            return status;
        }
    }

	public Date getFirst_execution_date() {
		return first_execution_date;
	}

	public void setFirst_execution_date(Date first_execution_date) {
		this.first_execution_date = first_execution_date;
	}

	public Date getLast_execution_date() {
		return last_execution_date;
	}

	public void setLast_execution_date(Date last_execution_date) {
		this.last_execution_date = last_execution_date;
	}
}
