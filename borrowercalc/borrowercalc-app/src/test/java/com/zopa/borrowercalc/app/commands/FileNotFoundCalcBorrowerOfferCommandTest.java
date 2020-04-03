package com.zopa.borrowercalc.app.commands;

import org.junit.Test;
import com.zopa.borrowercalc.commons.exampledata.ExampleData;
import com.zopa.borrowercalc.commons.exampledata.FileNotFoundExampleData;

import static org.assertj.core.api.Assertions.assertThat;

public class FileNotFoundCalcBorrowerOfferCommandTest extends AbstractCalcBorrowerOfferCommandTests {

	protected final static String MARKET_FILE_NOT_EXIST = "marketFile file does not exist";

	ExampleData exampleData = new FileNotFoundExampleData();

	@Test
	public void fileNotExistTest() throws Exception {
		String[] args = { exampleData.getDataFileName(), new Integer(MIN_LOAN_AMOUNT).toString() };
		calcBorrowerOfferCommand.run(args);
		assertThat(this.output.toString()).contains(MARKET_FILE_NOT_EXIST);
	}

}
