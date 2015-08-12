package me.figo.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Security {
	
	@Expose(serialize = false)
	private String security_id;
	
	@Expose(serialize = false)
	private String account_id;
	
	@Expose
	private String name;
	
	@Expose
	private String isin;
	
	@Expose
	private String wkn;
	
	@Expose
	private String currency;
	
	@Expose
	private int quantity;
	
	@Expose
	private BigDecimal amount;
	
	@Expose
	private BigDecimal amount_original_currency;
	
	@Expose
	private BigDecimal exchange_rate;
	
	@Expose
	private BigDecimal price;
	
	@Expose
	private String price_currency;
	
	@Expose
	private BigDecimal purchase_price;
	
	@Expose
	private boolean visited;
	
	@Expose(serialize = false)
	private Date trade_timestamp;
	
	@Expose(serialize = false)
	private Date creation_timestamp;
	
	@Expose(serialize = false)
	private Date modification_timestamp;

	public String getSecurityId() {
		return security_id;
	}

	public void setSecurityId(String securityId) {
		this.security_id = securityId;
	}

	public String getAccountId() {
		return account_id;
	}

	public void setAccountId(String accountId) {
		this.account_id = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getWkn() {
		return wkn;
	}

	public void setWkn(String wkn) {
		this.wkn = wkn;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountOriginalCurrency() {
		return amount_original_currency;
	}

	public void setAmountOriginalCurrency(BigDecimal amountOriginalCurrency) {
		this.amount_original_currency = amountOriginalCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchange_rate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchange_rate = exchangeRate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPriceCurrency() {
		return price_currency;
	}

	public void setPriceCurrency(String priceCurrency) {
		this.price_currency = priceCurrency;
	}

	public BigDecimal getPurchasePrice() {
		return purchase_price;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchase_price = purchasePrice;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Date getTradeTimestamp() {
		return trade_timestamp;
	}

	public void setTradeTimestamp(Date tradeTimestamp) {
		this.trade_timestamp = tradeTimestamp;
	}

	public Date getCreationTimestamp() {
		return creation_timestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creation_timestamp = creationTimestamp;
	}

	public Date getModificationTimestamp() {
		return modification_timestamp;
	}

	public void setModificationTimestamp(Date modificationTimestamp) {
		this.modification_timestamp = modificationTimestamp;
	}
	
	public static class SecurityResponse	{

		@Expose
		private List<Security> securities;
		
		@Expose
		private SynchronizationStatus status;
		
		public SecurityResponse()	{
			
		}
		
		public List<Security> getSecurities()	{
			return this.securities;
		}
		
		public SynchronizationStatus getStatus()	{
			return this.status;
		}
	}
}
