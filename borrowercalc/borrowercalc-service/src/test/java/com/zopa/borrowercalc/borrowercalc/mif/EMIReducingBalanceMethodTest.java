package com.zopa.borrowercalc.borrowercalc.mif;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.Test;

import com.zopa.borrowercalc.borrowercalc.mif.EMIReducingBalanceMethod;

public class EMIReducingBalanceMethodTest {

	EMIReducingBalanceMethod equatedMonthlyInstalments = new EMIReducingBalanceMethod();

	protected final static MathContext MATH_CONTEXT = new MathContext(6, RoundingMode.HALF_UP);

	protected final static int MONTH_LOANS = 36;

	@Test
	public void calc1Test() {
		int loanAmount = 1000;
		BigDecimal rate = BigDecimal.valueOf(0.07);
		int month = 3*12;

		BigDecimal monthlyInstalments = getEquatedMonthlyInstalments().calc(loanAmount, rate, month, MATH_CONTEXT);

		assertEquals(BigDecimal.valueOf(30.8771), monthlyInstalments);
	}

	public EMIReducingBalanceMethod getEquatedMonthlyInstalments() {
		return equatedMonthlyInstalments;
	}

	public void setEquatedMonthlyInstalments(EMIReducingBalanceMethod equatedMonthlyInstalments) {
		this.equatedMonthlyInstalments = equatedMonthlyInstalments;
	}
}
