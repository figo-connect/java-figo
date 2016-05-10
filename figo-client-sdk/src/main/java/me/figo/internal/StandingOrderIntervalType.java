package me.figo.internal;

import com.google.gson.annotations.SerializedName;

public enum StandingOrderIntervalType {
	
	@SerializedName("monthly")
	MONTHLY("monthly", 1),
	@SerializedName("two monthly")
	TWO_MONTHLY("two monthly", 2),
	@SerializedName("quarterly")
	QUARTERLY("quarterly", 3),
	@SerializedName("half yearly")
	HALF_YEARLY("half yearly", 6),
	@SerializedName("yearly")
	YEARLY("yearly", 12);
	
	private final String	interval;
	private int				month;

	private StandingOrderIntervalType(final String interval, final int month)
	{
		this.interval = interval;
		this.month = month;
	}
	
	StandingOrderIntervalType(final String interval)
	{
		this.interval = interval;
	}
	
	public static StandingOrderIntervalType fromInterval(final String interval)
	{
		for (StandingOrderIntervalType sot : StandingOrderIntervalType.values()) {
			if (interval == sot.interval) {
				return sot;
			}
		}
		return null;
	}

	public int getMonth()
	{
		return this.month;
	}

	public String getInterval()
	{
		return this.interval;
	}
}
