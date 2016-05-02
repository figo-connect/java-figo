package me.figo.models;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class AdditionalTransactionInfo {

    @Expose(serialize = false)
    private BigDecimal grossAmount;

    @Expose(serialize = false)
    private BigDecimal fees;

    public AdditionalTransactionInfo()  {
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public BigDecimal getFees() {
        return fees;
    }
}
