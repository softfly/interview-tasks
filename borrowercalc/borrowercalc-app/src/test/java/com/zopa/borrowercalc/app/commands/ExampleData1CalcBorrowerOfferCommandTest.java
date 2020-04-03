package com.zopa.borrowercalc.app.commands;

import org.junit.Test;

import com.zopa.borrowercalc.commons.exampledata.ExampleData;
import com.zopa.borrowercalc.commons.exampledata.ExampleData1;
import com.zopa.borrowercalc.model.entities.LenderOffer;

import static org.assertj.core.api.Assertions.assertThat;

public class ExampleData1CalcBorrowerOfferCommandTest extends AbstractCalcBorrowerOfferCommandTests {

	protected final static String NOT_ENOUGH_ARGUMENTS = "Not enough arguments.";

	protected final static String LOAN_AMOUNT_NOT_INTEGER = "loanAmount must be an integer";

	protected final static String LOAN_AMOUNT_MIN = "loanAmount must be greater than or equal to " + MIN_LOAN_AMOUNT;

	protected final static String LOAN_AMOUNT_MAX = "loanAmount must be less than or equal to " + MAX_LOAN_AMOUNT;

	protected final static String LOAN_AMOUNT_MULTIPLE = "loanAmount must be a multiple of 100";

	protected final static String APP_REQUESTED_AMOUNT = "Requested amount: £";

	protected final static String APP_RATE = "Rate: ";

	protected final static String APP_MONTHLY_REPAYMENT = "Monthly repayment: £";

	protected final static String APP_TOTAL_REPAYMENT = "Total repayment: £";

	protected final static String APP_NOT_BORROWER_OFFER = "It is not possible to provide a quote at that time.";

	ExampleData exampleData = new ExampleData1();

	@Test
	public void emptyTest() throws Exception {
		String[] args = {};
		calcBorrowerOfferCommand.run(args);
		assertThat(this.output.toString()).contains(NOT_ENOUGH_ARGUMENTS);
	}

	@Test
	public void empty1Test() throws Exception {
		String[] args = { exampleData.getDataFileName() };
		calcBorrowerOfferCommand.run(args);
		assertThat(this.output.toString()).contains(NOT_ENOUGH_ARGUMENTS);
	}

	@Test
	public void noIntegerTest() throws Exception {
		String[] args = { exampleData.getDataFileName(), "error" };
		calcBorrowerOfferCommand.run(args);
		assertThat(this.output.toString()).contains(LOAN_AMOUNT_NOT_INTEGER);
	}

	@Test
	public void minTest() throws Exception {
		String[] args = { exampleData.getDataFileName(), new Integer(MIN_LOAN_AMOUNT - 1).toString() };
		calcBorrowerOfferCommand.run(args);
		assertThat(this.output.toString()).contains(LOAN_AMOUNT_MIN);
	}

	@Test
	public void maxTest() throws Exception {
		String[] args = { exampleData.getDataFileName(), new Integer(MAX_LOAN_AMOUNT + 1).toString() };
		calcBorrowerOfferCommand.run(args);
		assertThat(this.output.toString()).contains(LOAN_AMOUNT_MAX);
	}

	@Test
	public void x100Test() throws Exception {
		String[] args = { exampleData.getDataFileName(), "1001" };
		calcBorrowerOfferCommand.run(args);
		assertThat(this.output.toString()).contains(LOAN_AMOUNT_MULTIPLE);
	}

	@Test
	public void displayOffer1Test() throws Exception {
		String[] args = { exampleData.getDataFileName(), "1000" };
		Thread.sleep(5000);
		calcBorrowerOfferCommand.run(args);

		String output = this.output.toString();
		assertThat(output).contains(APP_REQUESTED_AMOUNT + "1000");
		assertThat(output).contains(APP_RATE + "7.00%");
		assertThat(output).contains(APP_MONTHLY_REPAYMENT+ "30.88");
		assertThat(output).contains(APP_TOTAL_REPAYMENT + "1111.64");
	}

	@Test
	public void displayOffer2Test() throws Exception {
		int available = 0;
		for (LenderOffer offer : exampleData.getLenderOffers()) {
			available += offer.getAvailable();
		}

		Integer loanAmount = 1000;
		for (; loanAmount < (available < MAX_LOAN_AMOUNT ? available : MAX_LOAN_AMOUNT); loanAmount += 100) {
			String[] args = { ExampleData1.DATA_FILE_PATCH, loanAmount.toString() };
			calcBorrowerOfferCommand.run(args);

			String output = this.output.toString();
			assertThat(output).contains(APP_REQUESTED_AMOUNT + loanAmount);
			assertThat(output).contains(APP_RATE);
			assertThat(output).contains(APP_MONTHLY_REPAYMENT);
			assertThat(output).contains(APP_TOTAL_REPAYMENT);
		}
	}

	@Test
	public void displayNotOfferTest() throws Exception {
		int available = 0;
		for (LenderOffer offer : exampleData.getLenderOffers()) {
			available += offer.getAvailable();
		}
		available = ((available) / 100) * 100 + 100;

		Integer loanAmount = available;
		for (int i = 0; loanAmount < MAX_LOAN_AMOUNT && i < 5; i++) {
			String[] args = { ExampleData1.DATA_FILE_PATCH, loanAmount.toString() };

			calcBorrowerOfferCommand.run(args);

			assertThat(this.output.toString()).contains(APP_NOT_BORROWER_OFFER);
			loanAmount += 100;
		}
	}

}
