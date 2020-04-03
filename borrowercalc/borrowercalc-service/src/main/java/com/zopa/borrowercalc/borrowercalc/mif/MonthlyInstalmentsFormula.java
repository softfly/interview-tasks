package com.zopa.borrowercalc.borrowercalc.mif;

import java.math.BigDecimal;
import java.math.MathContext;

public interface MonthlyInstalmentsFormula {

	BigDecimal calc(int loanAmount, BigDecimal rate, int month, MathContext mathContext);

}
