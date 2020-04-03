package com.zopa.borrowercalc.commons.exampledata;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.zopa.borrowercalc.commons.TestConstans;
import com.zopa.borrowercalc.model.entities.LenderOffer;

public class UnparseExampleData implements ExampleData {

	private static final List<LenderOffer> LENDER_OFFERS = new ArrayList<LenderOffer>(7);
	static {
		LENDER_OFFERS.add(new LenderOffer("Angela", new BigDecimal("0.071"), 60));
	}

	private static final String DATA_FILE_PATCH = TestConstans.EXAMPLE_DATA_PATCH + "unparseExampleData.csv";

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
