package com.mortageCal.MortageCalculation.entities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class MortageInputs {
	
	@Autowired
	private static final Logger logger = LogManager.getLogger(MortageInputs.class);
	
	private double purchasePrice;
	private double depositePaid;
	private int bondTermYears;
	private double percentageRate;
	private String timeFrame;
	private String dataType;

	public MortageInputs(double purchasePrice, double depositePaid, int bondTermYears,
			double percentageRate, String timeFrame, String dataType) {
		super();
		this.purchasePrice = purchasePrice;
		this.depositePaid = depositePaid;
		this.bondTermYears = bondTermYears;
		this.percentageRate = percentageRate;
		this.timeFrame = timeFrame;
		this.dataType = dataType;
	}
	
	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public double getDepositePaid() {
		return depositePaid;
	}

	public void setDepositePaid(double depositePaid) {
		this.depositePaid = depositePaid;
	}

	public int getBondTermYears() {
		return bondTermYears;
	}

	public void setBondTermYears(int bondTermYears) {
		this.bondTermYears = bondTermYears;
	}

	public Double getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(double percentageRate) {
		this.percentageRate = percentageRate;
	}
	
	public String getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public String toString() {
		return "MortageInputs [purchasePrice=" + purchasePrice + ", depositePaid=" + depositePaid + ", bondTermYears="
				+ bondTermYears + ", percentageRate=" + percentageRate + ", timeFrame=" + timeFrame + ", dataType="
				+ dataType + "]";
	}

	public void log() {
	   logger.info("New user calculation: " + this.toString());
	   logger.info("----------------------");
	}
}
