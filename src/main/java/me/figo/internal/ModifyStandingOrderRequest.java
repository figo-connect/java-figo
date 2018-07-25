package me.figo.internal;


import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.Expose;

import me.figo.models.StandingOrder;

public class ModifyStandingOrderRequest {

    @Expose
    private String standing_order_id;

    @Expose
    private BigDecimal amount;

    @Expose
    private String name;

    @Expose
    private String purpose;

    @Expose
    private StandingOrderIntervalType interval;

    @Expose
    private Integer execution_day;

    @Expose
    private Date first_execution_date;

    @Expose
    private Date last_execution_date;

    public ModifyStandingOrderRequest(StandingOrder modifiedStandingOrder)  {
        this.setStandingOrderId(modifiedStandingOrder.getStandingOrderId());
        this.setAmount(modifiedStandingOrder.getAmount());
        this.setName(modifiedStandingOrder.getName());
        this.setPurpose(modifiedStandingOrder.getPurposeText());
        this.setInterval(modifiedStandingOrder.getInterval());
        this.setExecutionDay(modifiedStandingOrder.getExecutionDay());
        this.setFirstExecutionDate(modifiedStandingOrder.getFirstExecutionDate());
        this.setLastExecutionDate(modifiedStandingOrder.getLastExecutionDate());
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Date getFirstExecutionDate() {
        return first_execution_date;
    }

    public void setFirstExecutionDate(Date first_execution_date) {
        this.first_execution_date = first_execution_date;
    }

    public Date getLastExecutionDate() {
        return last_execution_date;
    }

    public void setLastExecutionDate(Date last_execution_date) {
        this.last_execution_date = last_execution_date;
    }
}
