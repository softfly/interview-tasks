package com.zopa.borrowercalc.app.commands;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.zopa.borrowercalc.app.cli.BorrowerCalculatorCLI;
import com.zopa.borrowercalc.app.cli.BorrowerCalculatorInputDTO;
import com.zopa.borrowercalc.borrowercalc.service.BorrowerOfferPort;
import com.zopa.borrowercalc.borrowercalc.service.BorrowerOfferPortService;
import com.zopa.borrowercalc.commons.parsers.LenderOffersParser;
import com.zopa.borrowercalc.model.entities.BorrowerOffer;
import com.zopa.borrowercalc.model.entities.LenderOffer;
import static com.zopa.borrowercalc.app.mappers.BorrowerOfferWebServiceMapper.*;

@Component
public class CalcBorrowerOfferCommand implements CommandLineRunner {

	@Autowired
	private BorrowerCalculatorCLI borrowerCalculatorCLI;

	@Autowired
	private LenderOffersParser lenderOffersParser;

	private BorrowerOfferPortService service = new BorrowerOfferPortService();

	private BorrowerOfferPort borrowerOfferPort = service.getBorrowerOfferPortSoap11();

	@Autowired
	private Environment environment;

	@Override
	public void run(String... args) throws Exception {
		BorrowerCalculatorInputDTO inputDTO = getBorrowerCalculatorCLI().parseInput(args);
		if (inputDTO != null && getBorrowerCalculatorCLI().validInput(inputDTO)) {

			// Enrich repository
			List<LenderOffer> lenderOffers = getLenderOffersParser().parse(inputDTO.getMarketFile());
			borrowerOfferPort.setLenderOffers(mapping(lenderOffers));

			// Calculate borrower offer
			BorrowerOffer borrowerOffer = mapping(getBorrowerOfferPort().calcBorrowerOffer(mapping(inputDTO)));

			getBorrowerCalculatorCLI().displayBorrowerOffer(borrowerOffer);
		}

		if (!Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			System.exit(0);
		}
	}

	protected BorrowerCalculatorCLI getBorrowerCalculatorCLI() {
		return borrowerCalculatorCLI;
	}

	protected void setBorrowerCalculatorCLI(BorrowerCalculatorCLI borrowerCalculatorCLI) {
		this.borrowerCalculatorCLI = borrowerCalculatorCLI;
	}

	protected LenderOffersParser getLenderOffersParser() {
		return lenderOffersParser;
	}

	protected void setLenderOffersParser(LenderOffersParser lenderOffersParser) {
		this.lenderOffersParser = lenderOffersParser;
	}

	protected BorrowerOfferPort getBorrowerOfferPort() {
		return borrowerOfferPort;
	}

	protected void setBorrowerOfferPort(BorrowerOfferPort borrowerOfferPort) {
		this.borrowerOfferPort = borrowerOfferPort;
	}

	protected Environment getEnvironment() {
		return environment;
	}

	protected void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
