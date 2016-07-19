package me.figo.internal;


import com.google.gson.annotations.Expose;
import me.figo.models.StandingOrder;

import java.math.BigDecimal;

public class ModifyStandingOrderRequest {

    @Expose
    private String standing_order_id;

    @Expose
    private String name;

    @Expose
    private String account_number;

    @Expose
    private String bank_code;

    @Expose
    private BigDecimal amount;

    @Expose
    private String currency;

    @Expose
    private String purpose;

    @Expose
    private Integer execution_day;

    @Expose
    private StandingOrderIntervalType interval;

    public ModifyStandingOrderRequest(StandingOrder modifiedStandingOrder)  {
        this.setStandingOrderId(modifiedStandingOrder.getStandingOrderId());
        this.setName(modifiedStandingOrder.getName());
        this.setAccountNumber(modifiedStandingOrder.getAccountNumber());
        this.setBankCode(modifiedStandingOrder.getBankCode());
        this.setAmount(modifiedStandingOrder.getAmount());
        this.setCurrency(modifiedStandingOrder.getCurrency());
        this.setPurpose(modifiedStandingOrder.getPurposeText());
        this.setExecutionDay(modifiedStandingOrder.getExecutionDay());
        this.setInterval(modifiedStandingOrder.getInterval());
    }

    public String getStandingOrderId() {
        return standing_order_id;
    }

    public void setStandingOrderId(String standing_order_id) {
        this.standing_order_id = standing_order_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return account_number;
    }

    public void setAccountNumber(String account_number) {
        this.account_number = account_number;
    }

    public String getBankCode() {
        return bank_code;
    }

    public void setBankCode(String bank_code) {
        this.bank_code = bank_code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getExecutionDay() {
        return execution_day;
    }

    public void setExecutionDay(Integer execution_day) {
        this.execution_day = execution_day;
    }

    public StandingOrderIntervalType getInterval() {
        return interval;
    }

    public void setInterval(StandingOrderIntervalType interval) {
        this.interval = interval;
    }
}
