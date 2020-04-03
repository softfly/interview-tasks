package com.zopa.borrowercalc.borrowercalc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

import com.zopa.borrowercalc.borrowercalc.mif.EMIReducingBalanceMethod;
import com.zopa.borrowercalc.borrowercalc.repositories.LenderOfferRepositoryBean;
import com.zopa.borrowercalc.commons.exampledata.ExampleData;
import com.zopa.borrowercalc.commons.exampledata.ExampleData1;
import com.zopa.borrowercalc.model.entities.BorrowerOffer;
import com.zopa.borrowercalc.model.entities.LenderOffer;;

public class BorrowerCalculatorBeanTest {

	private ExampleData exampleData = new ExampleData1();

	private BorrowerCalculatorBean borrowerCalculator = new BorrowerCalculatorBean();
	{
		borrowerCalculator.setMonthlyInstalmentsFormula(new EMIReducingBalanceMethod());
		borrowerCalculator.setLenderOfferRepository(new LenderOfferRepositoryBean());
		borrowerCalculator.setLenderOffers(exampleData.getLenderOffers());
	}


	@Test
	public void determineLenderOffersHappyTest() {
		int loanAmount = 1001;
		List<LenderOffer> lenderOffers = getBorrowerCalculator().determineLenderOffers(loanAmount);

		int sum=0;
		for (LenderOffer lenderOffer: lenderOffers) {
			sum += lenderOffer.getAvailable();
		}
		assertEquals(loanAmount, sum);
	}

	@Test
	public void determineLenderOffersMaxTest() {
		int loanAmount = Integer.MAX_VALUE;
		List<LenderOffer> lenderOffers = getBorrowerCalculator().determineLenderOffers(loanAmount);
		assertNull(lenderOffers);
	}

	@Test
	public void calcRateTest() {
		int loanAmount = 1000;
		List<LenderOffer> lenderOffers = new LinkedList<LenderOffer>();
		lenderOffers.add(new LenderOffer("Jane", new BigDecimal("0.069"), 480));
		lenderOffers.add(new LenderOffer("Fred", new BigDecimal("0.071"), 520));

		BigDecimal rate = getBorrowerCalculator().calcRate(lenderOffers, loanAmount);

		assertEquals(BigDecimal.valueOf(0.07004), rate);
	}

	@Test
	public void calcBorrowerOfferHappyTest() {
		int loanAmount = 1000;
		int month = 36;

		BorrowerOffer borrowerOfferDTO = getBorrowerCalculator().calcBorrowerOffer(loanAmount, month);

		assertEquals(BigDecimal.valueOf(0.07004), borrowerOfferDTO.getRate());
		assertEquals(BigDecimal.valueOf(30.8789), borrowerOfferDTO.getMonthlyRepayment());
		assertEquals(BigDecimal.valueOf(11116404, 4), borrowerOfferDTO.getTotalRepayment());
	}

	@Test(expected = IllegalArgumentException.class)
	public void calcBorrowerOfferMinLoanAmountTest() {
		int loanAmount = 0;
		int month = 36;

		getBorrowerCalculator().calcBorrowerOffer(loanAmount, month);
	}

	@Test(expected = IllegalArgumentException.class)
	public void calcBorrowerOfferMinMonthTest() {
		int loanAmount = 1000;
		int month = 0;

		getBorrowerCalculator().calcBorrowerOffer(loanAmount, month);
	}

	public BorrowerCalculatorBean getBorrowerCalculator() {
		return borrowerCalculator;
	}

	public void setBorrowerCalculator(BorrowerCalculatorBean borrowerCalculator) {
		this.borrowerCalculator = borrowerCalculator;
	}

}
