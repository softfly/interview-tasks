package com.zopa.borrowercalc.commons.exampledata;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.zopa.borrowercalc.commons.TestConstans;
import com.zopa.borrowercalc.model.entities.LenderOffer;

public class ExampleData1 implements ExampleData {

	private static final List<LenderOffer> LENDER_OFFERS = new ArrayList<LenderOffer>(7);
	static {
		LENDER_OFFERS.add(new LenderOffer("Jane", new BigDecimal("0.069"), 480));
		LENDER_OFFERS.add(new LenderOffer("Fred", new BigDecimal("0.071"), 520));
		LENDER_OFFERS.add(new LenderOffer("Angela", new BigDecimal("0.071"), 60));
		LENDER_OFFERS.add(new LenderOffer("Dave", new BigDecimal("0.074"), 140));
		LENDER_OFFERS.add(new LenderOffer("Bob", new BigDecimal("0.075"), 640));
		LENDER_OFFERS.add(new LenderOffer("John", new BigDecimal("0.081"), 320));
		LENDER_OFFERS.add(new LenderOffer("Mary", new BigDecimal("0.104"), 170));
	}

	public static final String DATA_FILE_PATCH = TestConstans.EXAMPLE_DATA_PATCH + "exampleData1.csv";

	private static File DATA_FILE = new File(DATA_FILE_PATCH);

	@Override
	public List<LenderOffer> getLenderOffers() {
		return LENDER_OFFERS;
	}

	@Override
	public String getDataFileName() {
		return DATA_FILE_PATCH;
	}

	@Override
	public File getDataFile() {
		return DATA_FILE;
	}

}
