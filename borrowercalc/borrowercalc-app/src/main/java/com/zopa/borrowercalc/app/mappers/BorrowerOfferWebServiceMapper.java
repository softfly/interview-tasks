package com.zopa.borrowercalc.app.mappers;

import java.util.List;

import com.zopa.borrowercalc.app.cli.BorrowerCalculatorInputDTO;
import com.zopa.borrowercalc.borrowercalc.service.CalcBorrowerOfferRequest;
import com.zopa.borrowercalc.borrowercalc.service.CalcBorrowerOfferResponse;
import com.zopa.borrowercalc.borrowercalc.service.SetLenderOffersRequest;
import com.zopa.borrowercalc.model.entities.BorrowerOffer;
import com.zopa.borrowercalc.model.entities.LenderOffer;

public class BorrowerOfferWebServiceMapper {

	protected final static int MONTH_LOANS = 36;

	static public BorrowerOffer mapping(CalcBorrowerOfferResponse source) {
		if (source.getBorrowerOffer() != null) {
			return mapping(source.getBorrowerOffer());
		} else {
			return null;
		}
	}

	static public BorrowerOffer mapping(com.zopa.borrowercalc.borrowercalc.service.BorrowerOffer source) {
		BorrowerOffer target = new BorrowerOffer();
		target.setMonthlyRepayment(source.getMonthlyRepayment());
		target.setRequestedAmount(source.getRequestedAmount());
		target.setRate(source.getRate());
		target.setTotalRepayment(source.getTotalRepayment());
		return target;
	}

	static public CalcBorrowerOfferRequest mapping(BorrowerCalculatorInputDTO source) {
		CalcBorrowerOfferRequest target = new CalcBorrowerOfferRequest();
		target.setLoanAmount(source.getLoanAmount());
		target.setMonth(MONTH_LOANS);
		return target;
	}

	static public SetLenderOffersRequest mapping(List<LenderOffer> source) {
		SetLenderOffersRequest target = new SetLenderOffersRequest();
		for (LenderOffer lenderOffer : source) {
			target.getLenderOffer().add(mapping(lenderOffer));
		}
		return target;
	}

	static public com.zopa.borrowercalc.borrowercalc.service.LenderOffer mapping(LenderOffer source) {
		com.zopa.borrowercalc.borrowercalc.service.LenderOffer target = new com.zopa.borrowercalc.borrowercalc.service.LenderOffer();
		target.setName(source.getName());
		target.setRate(source.getRate());
		target.setAvailable(source.getAvailable());
		return target;
	}

}
