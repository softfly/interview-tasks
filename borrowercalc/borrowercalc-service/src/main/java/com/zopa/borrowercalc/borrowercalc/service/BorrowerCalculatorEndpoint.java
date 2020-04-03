package com.zopa.borrowercalc.borrowercalc.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.zopa.borrowercalc.model.entities.BorrowerOffer;
import com.zopa.borrowercalc.model.entities.LenderOffer;
import static com.zopa.borrowercalc.borrowercalc.mappers.BorrowerCalculatorWebServiceMapper.*;

@Endpoint
public class BorrowerCalculatorEndpoint {

	private static final String NAMESPACE_URI = "http://zopa.com/borrowercalc/borrowercalc/service";

	@Autowired
	private BorrowerCalculator borrowerCalculator;

	@PayloadRoot(namespace=NAMESPACE_URI, localPart = "calcBorrowerOfferRequest")
	@ResponsePayload
	public CalcBorrowerOfferResponse calcBorrowerOffer(
			@RequestPayload CalcBorrowerOfferRequest calcBorrowerOfferRequest) {

		BorrowerOffer borrowerOffer = getBorrowerCalculator().calcBorrowerOffer(calcBorrowerOfferRequest.getLoanAmount(),
				calcBorrowerOfferRequest.getMonth());

		CalcBorrowerOfferResponse response = new CalcBorrowerOfferResponse();
		if (borrowerOffer != null) {
			response.setBorrowerOffer(mapping(borrowerOffer));
		}
		return response;
	}

	@PayloadRoot(namespace=NAMESPACE_URI, localPart = "setLenderOffersRequest")
	@ResponsePayload
	public void addLenderOffers(@RequestPayload SetLenderOffersRequest setLenderOffersRequest) {
		List<LenderOffer> lenderOffers = new LinkedList<>();
		for (com.zopa.borrowercalc.borrowercalc.service.LenderOffer source : setLenderOffersRequest.getLenderOffer()) {
			lenderOffers.add(mapping(source));
		}
		getBorrowerCalculator().setLenderOffers(lenderOffers);
	}

	protected BorrowerCalculator getBorrowerCalculator() {
		return borrowerCalculator;
	}

	protected void setBorrowerCalculator(BorrowerCalculator borrowerCalculator) {
		this.borrowerCalculator = borrowerCalculator;
	}

}
