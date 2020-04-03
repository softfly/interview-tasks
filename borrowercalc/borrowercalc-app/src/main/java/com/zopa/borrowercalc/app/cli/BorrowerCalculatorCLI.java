package com.zopa.borrowercalc.app.cli;

import static com.zopa.borrowercalc.commons.utils.NumberUtil.isInteger;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.zopa.borrowercalc.commons.utils.MessageSourceUtil;
import com.zopa.borrowercalc.commons.utils.ValidatorUtil;
import com.zopa.borrowercalc.model.entities.BorrowerOffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Communication between the command line interface and the application.
 */
@Service
public class BorrowerCalculatorCLI {

	protected final static String APP_HELP = "app.help";

	// Validation
	protected final static String NOT_ENOUGH_ARGUMENTS = "not.enough.arguments";

	protected final static String NOT_INTEGER = "loanAmount.not.integer";

	// Borrower offer
	protected final static String APP_REQUESTED_AMOUNT = "app.borrower.offer.requested.amount";

	protected final static String APP_RATE = "app.borrower.offer.rate";

	protected final static String APP_MONTHLY_REPAYMENT = "app.borrower.offer.monthly.repayment";

	protected final static String APP_TOTAL_REPAYMENT = "app.borrower.offer.total.repayment";

	protected final static String APP_NOT_BORROWER_OFFER = "app.not.borrower.offer";

	@Autowired
	private MessageSourceUtil messageSource;

	@Autowired
	private ValidatorUtil validator;

	public BorrowerCalculatorInputDTO parseInput(String[] args) {
		if (args.length < 2) {
			System.out.println(getMessage(NOT_ENOUGH_ARGUMENTS));
			System.out.println();
			displayHelp();
			return null;
		}
		BorrowerCalculatorInputDTO inputDTO = new BorrowerCalculatorInputDTO();
		inputDTO.setMarketFile(new File(args[0]));
		if (isInteger(args[1])) {
			inputDTO.setLoanAmount(Integer.valueOf(args[1]));
		} else {
			System.out.println(getMessage(NOT_INTEGER));
			System.out.println();
			displayHelp();
			return null;
		}
		return inputDTO;
	}

	public boolean validInput(BorrowerCalculatorInputDTO inputDTO) {
		return validator.valid(inputDTO);
	}

	public void displayBorrowerOffer(BorrowerOffer borrowerOfferDTO) {
		if (borrowerOfferDTO != null) {
			System.out.printf(getMessage(APP_REQUESTED_AMOUNT) + "\n", borrowerOfferDTO.getRequestedAmount());
			System.out.printf(getMessage(APP_RATE) + "\n", borrowerOfferDTO.getRate().multiply(BigDecimal.valueOf(100))
					.setScale(2, RoundingMode.HALF_UP).floatValue());
			System.out.printf(getMessage(APP_MONTHLY_REPAYMENT) + "\n", borrowerOfferDTO.getMonthlyRepayment().floatValue());
			System.out.printf(getMessage(APP_TOTAL_REPAYMENT) + "\n", borrowerOfferDTO.getTotalRepayment().floatValue());
		} else {
			System.out.println(getMessage(APP_NOT_BORROWER_OFFER));
		}
	}

	public void displayHelp() {
		System.out.println(getMessage(APP_HELP));
	}

	protected String getMessage(String code) {
		return messageSource.getMessage(code);
	}

}
