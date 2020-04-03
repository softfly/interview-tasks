package com.zopa.borrowercalc.borrowercalc.mappers;

import com.zopa.borrowercalc.model.entities.BorrowerOffer;
import com.zopa.borrowercalc.model.entities.LenderOffer;

public class BorrowerCalculatorWebServiceMapper {

	static public com.zopa.borrowercalc.borrowercalc.service.BorrowerOffer mapping(BorrowerOffer source) {
		com.zopa.borrowercalc.borrowercalc.service.BorrowerOffer target = new com.zopa.borrowercalc.borrowercalc.service.BorrowerOffer();
		target.setMonthlyRepayment(source.getMonthlyRepayment());
		target.setRate(source.getRate());
		target.setRequestedAmount(source.getRequestedAmount());
		target.setTotalRepayment(source.getTotalRepayment());
		return target;
	}

	static public LenderOffer mapping(com.zopa.borrowercalc.borrowercalc.service.LenderOffer source) {
		LenderOffer target = new LenderOffer();
		target.setAvailable(source.getAvailable());
		target.setName(source.getName());
		target.setRate(source.getRate());
		return target;
	}

}
