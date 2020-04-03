package com.zopa.borrowercalc.borrowercalc.mif;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import org.springframework.stereotype.Service;

/**
 * EquatedMonthlyInstalments
 * EMI reducing balance method
 *
 * Math formula from:
 * http://rmathew.com/2006/calculating-emis.html
 */
@Service
public class EMIReducingBalanceMethod implements MonthlyInstalmentsFormula {

	protected final static String EXCEPTION_LOAN_AMOUNT_MIN = "loanAmount must be greater than or equal to 1";

	protected final static String EXCEPTION_MONTH_MIN = "month must be greater than or equal to 1";

	protected final static String EXCEPTION_RATE_MIN = "rate must be greater than or equal to 1";

	@Override
	public BigDecimal calc(int loanAmount, BigDecimal rate, int month, MathContext mathContext) {
		Objects.nonNull(mathContext);

		if (loanAmount <= 0) {
			throw new IllegalArgumentException(EXCEPTION_LOAN_AMOUNT_MIN);
		}
		if (month <= 0) {
			throw new IllegalArgumentException(EXCEPTION_MONTH_MIN);
		}
		Objects.nonNull(rate);
		if (rate.intValue() < 0) {
			throw new IllegalArgumentException(EXCEPTION_RATE_MIN);
		}

		BigDecimal P = BigDecimal.valueOf(loanAmount);// P is principal amount borrowed
		BigDecimal I = rate;// I is the annual interest rate
		BigDecimal r = I.divide(BigDecimal.valueOf(12), mathContext);// r is periodic monthly interest rate
		int n = month;

		// (1+R)^N
		BigDecimal r1n = r.add(BigDecimal.ONE).pow(n);

		// (1+R)^N-1
		BigDecimal r1n1 = r1n.subtract(BigDecimal.ONE);

		// P x R x (1+R)^N / (1+R)^N-1
		return P.multiply(r).multiply(r1n).divide(r1n1, mathContext);
	}

}
