package com.zopa.borrowercalc.borrowercalc.comparators;

import java.util.Comparator;

import com.zopa.borrowercalc.model.entities.LenderOffer;


public class RateAscLenderOfferComparator implements Comparator<LenderOffer> {

	@Override
	public int compare(LenderOffer o1, LenderOffer o2) {
		int rate = o1.getRate().compareTo(o2.getRate());
		if (rate != 0) {
			return rate;
		}

		int available = o2.getAvailable().compareTo(o1.getAvailable());
		if (available != 0) {
			return available;
		}

		int name = o1.getName().compareTo(o2.getName());
		if (name != 0) {
			return name;
		}

		return 1;
	}

}
