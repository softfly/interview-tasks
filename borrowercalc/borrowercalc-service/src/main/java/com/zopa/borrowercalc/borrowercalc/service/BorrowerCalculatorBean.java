package com.zopa.borrowercalc.borrowercalc.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zopa.borrowercalc.borrowercalc.mif.MonthlyInstalmentsFormula;
import com.zopa.borrowercalc.borrowercalc.repositories.LenderOfferRepository;
import com.zopa.borrowercalc.model.entities.BorrowerOffer;
import com.zopa.borrowercalc.model.entities.LenderOffer;

/**
 * {@inheritDoc}
 */
@Service
public class BorrowerCalculatorBean implements BorrowerCalculator {

	private final static String EXCEPTION_LOAN_AMOUNT_MIN = "loanAmount must be greater than or equal to 1";

	private final static String EXCEPTION_MONTH_MIN = "month must be greater than or equal to 1";

	@Autowired
	private LenderOfferRepository lenderOfferRepository;

	@Autowired
	private MonthlyInstalmentsFormula monthlyInstalmentsFormula;

	private final static MathContext MATH_CONTEXT = new MathContext(6, RoundingMode.HALF_UP);

	@Override
	public BorrowerOffer calcBorrowerOffer(int loanAmount, int month) {
		if (loanAmount <= 0) {
			throw new IllegalArgumentException(EXCEPTION_LOAN_AMOUNT_MIN);
		}
		if (month <= 0) {
			throw new IllegalArgumentException(EXCEPTION_MONTH_MIN);
		}
		BorrowerOffer borrowerOffer = new BorrowerOffer();

		List<LenderOffer> lenderOffers = determineLenderOffers(loanAmount);
		if (lenderOffers != null && !lenderOffers.isEmpty()) {
			borrowerOffer.setRequestedAmount(loanAmount);
			borrowerOffer.setRate(calcRate(lenderOffers, loanAmount));
			borrowerOffer.setMonthlyRepayment(calcEMI(loanAmount, borrowerOffer.getRate(), month));
			borrowerOffer.setTotalRepayment(calcTotalRepayment(borrowerOffer.getMonthlyRepayment(), month));

			return borrowerOffer;
		} else {
			return null;
		}
	}

	/**
	 * Choose the best lender offers (low rate) for a borrower where loanAmount =
	 * sum(lenderOffers.avaible)
	 *
	 * The last lender offer is compensated to the missing loan amount.
	 *
	 * @param loanAmount min=1
	 * @return NULL - There cannot be determine lender offers for loanAmount.
	 */
	protected List<LenderOffer> determineLenderOffers(int loanAmount) {
		List<LenderOffer> lenderOffers = new LinkedList<>();

		Integer sum = 0;
		for (LenderOffer lenderOffer : getLenderOfferRepository().findAllOrderByRateAsc()) {
			sum += lenderOffer.getAvailable();
			lenderOffers.add(lenderOffer);
			if (sum >= loanAmount) {
				// The last lender offer is compensated to the missing loan amount.
				int align = sum - loanAmount;
				lenderOffer.setAvailable(lenderOffer.getAvailable() - align);
				return lenderOffers;
			}
		}

		return null;
	}

	protected BigDecimal calcRate(List<LenderOffer> lenderOffers, int loanAmount) {
		BigDecimal rate = BigDecimal.ZERO;
		for (LenderOffer lenderOffer : lenderOffers) {
			BigDecimal available = BigDecimal.valueOf(lenderOffer.getAvailable());
			BigDecimal p = available.divide(BigDecimal.valueOf(loanAmount), getMathContext());
			BigDecimal r = p.multiply(lenderOffer.getRate());
			rate = rate.add(r);
		}
		return rate;
	}

	protected BigDecimal calcEMI(int loanAmount, BigDecimal rate, int month) {
		return getMonthlyInstalmentsFormula().calc(loanAmount, rate, month, getMathContext());
	}

	protected BigDecimal calcTotalRepayment(BigDecimal emi, int month) {
		return emi.multiply(BigDecimal.valueOf(month));
	}

	public void setLenderOffers(Collection<LenderOffer> lenderOffers) {
		lenderOfferRepository.setLenderOffers(lenderOffers);
	}

	protected LenderOfferRepository getLenderOfferRepository() {
		return lenderOfferRepository;
	}

	protected void setLenderOfferRepository(LenderOfferRepository lenderOfferRepository) {
		this.lenderOfferRepository = lenderOfferRepository;
	}

	protected MathContext getMathContext() {
		return MATH_CONTEXT;
	}

	protected MonthlyInstalmentsFormula getMonthlyInstalmentsFormula() {
		return monthlyInstalmentsFormula;
	}

	protected void setMonthlyInstalmentsFormula(MonthlyInstalmentsFormula monthlyInstalmentsFormula) {
		this.monthlyInstalmentsFormula = monthlyInstalmentsFormula;
	}
}
