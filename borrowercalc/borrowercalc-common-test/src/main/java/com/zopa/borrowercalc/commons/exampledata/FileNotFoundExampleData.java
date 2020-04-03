package com.zopa.borrowercalc.commons.exampledata;

import java.io.File;
import java.util.Collection;

import com.zopa.borrowercalc.model.entities.LenderOffer;

public class FileNotFoundExampleData implements ExampleData {

	public static final String DATA_FILE_PATCH = "fileNotFound.csv";

	private static File DATA_FILE = new File(DATA_FILE_PATCH);

	@Override
	public Collection<LenderOffer> getLenderOffers() {
		return null;
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
