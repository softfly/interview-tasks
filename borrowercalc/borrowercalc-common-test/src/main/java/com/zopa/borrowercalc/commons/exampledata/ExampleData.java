package com.zopa.borrowercalc.commons.exampledata;

import java.io.File;
import java.util.Collection;

import com.zopa.borrowercalc.model.entities.LenderOffer;

public interface ExampleData {

	Collection<LenderOffer> getLenderOffers();

	String getDataFileName();

	File getDataFile();

}
