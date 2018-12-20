package me.figo.models;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;

public class AdditionalTransactionInfo {

    @Expose(serialize = false)
    private BigDecimal grossAmount;

    @Expose(serialize = false)
    private BigDecimal fees;

	@Expose(serialize = false)
	private BigDecimal compensation_amount;

	@Expose(serialize = false)
	private BigDecimal original_amount;

	@Expose(serialize = false)
	private String bank_reference;

	@Expose(serialize = false)
	private String debitor_id;

	@Expose(serialize = false)
	private String reference_party_creditor;

	@Expose(serialize = false)
	private String reference_party_debitor;

    public AdditionalTransactionInfo()  {
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public BigDecimal getFees() {
        return fees;
    }

	public BigDecimal getCompensation_amount() {
		return compensation_amount;
	}

	public void setCompensation_amount(BigDecimal compensation_amount) {
		this.compensation_amount = compensation_amount;
	}

	public BigDecimal getOriginal_amount() {
		return original_amount;
	}

	public void setOriginal_amount(BigDecimal original_amount) {
		this.original_amount = original_amount;
	}

	public String getBank_reference() {
		return bank_reference;
	}

	public void setBank_reference(String bank_reference) {
		this.bank_reference = bank_reference;
	}

	public String getDebitor_id() {
		return debitor_id;
	}

	public void setDebitor_id(String debitor_id) {
		this.debitor_id = debitor_id;
	}

	public String getReference_party_creditor() {
		return reference_party_creditor;
	}

	public void setReference_party_creditor(String reference_party_creditor) {
		this.reference_party_creditor = reference_party_creditor;
	}

	public String getReference_party_debitor() {
		return reference_party_debitor;
	}

	public void setReference_party_debitor(String reference_party_debitor) {
		this.reference_party_debitor = reference_party_debitor;
	}
}
