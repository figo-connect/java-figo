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

import java.util.Date;
import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

/**
 * Object representing the balance of a certain bank account of the user
 *
 * @author Stefan Richter
 */
public class AccountBalance {
    /**
     * Account balance or None if the balance is not yet known
     */
    @Expose(serialize = false)
    private BigDecimal balance;

    /**
     * Bank server timestamp of balance or None if the balance is not yet known.
     */
    @Expose(serialize = false)
    private Date  balance_date;

    /**
     * Credit line
     */
    @Expose
    private BigDecimal credit_line;

    /**
     * User-defined spending limit
     */
    @Expose
    private BigDecimal monthly_spending_limit;

    public AccountBalance() {
    }

    /**
     * @return the account balance or null if the balance is not yet known
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * @return the bank server timestamp of balance or null if the balance is not yet known.
     */
    public Date getBalanceDate() {
        return balance_date;
    }

    /**
     * @return the credit line
     */
    public BigDecimal getCreditLine() {
        return credit_line;
    }

    /**
     * @param credit_line
     *            the credit line to set
     */
    public void setCreditLine(BigDecimal credit_line) {
        this.credit_line = credit_line;
    }

    /**
     * @return the user-defined spending limit
     */
    public BigDecimal getMonthlySpendingLimit() {
        return monthly_spending_limit;
    }

    /**
     * @param spending_limit
     *            the user-defined spending limit to set
     */
    public void setMonthlySpendingLimit(BigDecimal spending_limit) {
        this.monthly_spending_limit = spending_limit;
    }
}
