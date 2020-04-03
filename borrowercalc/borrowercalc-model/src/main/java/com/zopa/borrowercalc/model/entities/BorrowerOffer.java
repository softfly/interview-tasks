package com.zopa.borrowercalc.model.entities;

import java.math.BigDecimal;

public class BorrowerOffer {

	private Integer requestedAmount;

	private BigDecimal rate;

	private BigDecimal monthlyRepayment;

	private BigDecimal totalRepayment;

	public Integer getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(Integer requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getMonthlyRepayment() {
		return monthlyRepayment;
	}

	public void setMonthlyRepayment(BigDecimal monthlyRepayment) {
		this.monthlyRepayment = monthlyRepayment;
	}

	public BigDecimal getTotalRepayment() {
		return totalRepayment;
	}

	public void setTotalRepayment(BigDecimal totalRepayment) {
		this.totalRepayment = totalRepayment;
	}

}
