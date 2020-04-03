package com.zopa.borrowercalc.common.parsers;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import com.zopa.borrowercalc.model.entities.LenderOffer;
import com.zopa.borrowercalc.commons.exampledata.ExampleData;
import com.zopa.borrowercalc.commons.exampledata.ExampleData1;
import com.zopa.borrowercalc.commons.exampledata.FileNotFoundExampleData;
import com.zopa.borrowercalc.commons.exampledata.UnparseExampleData;
import com.zopa.borrowercalc.commons.parsers.LenderOffersCsvParserBean;

public class LenderOffersCsvParserTest {

	LenderOffersCsvParserBean lendersCsvParser = new LenderOffersCsvParserBean();

	protected void test(ExampleData exampleData) throws FileNotFoundException {
		File marketFileCsv = exampleData.getDataFile();
		List<LenderOffer> lenderOffers = getLendersCsvParser().parse(marketFileCsv);

		//System.out.println(Arrays.toString(lenderOffers.toArray()));
		//System.out.println(Arrays.toString(exampleData.getLenderOffers().toArray()));

		assertTrue(CollectionUtils.isEqualCollection(exampleData.getLenderOffers(), lenderOffers));
	}

	@Test(expected = FileNotFoundException.class)
	public void fileNotFoundTest() throws FileNotFoundException {
		test(new FileNotFoundExampleData());
	}

	@Test
	public void unparseExampleDataTest() throws FileNotFoundException {
		test(new UnparseExampleData());
		//TODO optional check logs
	}

	@Test
	public void exampleData1Test() throws FileNotFoundException {
		test(new ExampleData1());
	}

	//TODO next tests with other example data exampleData2Test

	protected LenderOffersCsvParserBean getLendersCsvParser() {
		return lendersCsvParser;
	}

	protected void setLendersCsvParser(LenderOffersCsvParserBean lendersCsvParser) {
		this.lendersCsvParser = lendersCsvParser;
	}

}
