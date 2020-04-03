package com.zopa.borrowercalc.borrowercalc.service;

import java.util.Collection;

import com.zopa.borrowercalc.model.entities.BorrowerOffer;
import com.zopa.borrowercalc.model.entities.LenderOffer;

/**
 * Calculate Borrower Offer:
 *  - Requested amount: £XXXX
 *  - Rate: X.X%
 *  - Monthly repayment: £XXXX.XX
 *  - Total repayment: £XXXX.XX
 *  for amount and month
 */
public interface BorrowerCalculator {

	BorrowerOffer calcBorrowerOffer(int loanAmount, int month);

	void setLenderOffers(Collection<LenderOffer> lenderOffers);
}
