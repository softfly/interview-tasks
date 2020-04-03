package com.zopa.borrowercalc.commons.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zopa.borrowercalc.commons.utils.MessageSourceUtil;
import com.zopa.borrowercalc.model.entities.LenderOffer;

/**
 * Parser for CSV file. Example format: Lender,Rate,Available Bob,0.075,640
 *
 * TODO optional Recognize the fields in random order.
 * TODO optional Recognize the fields by order in the header.
 */
@Component
public class LenderOffersCsvParserBean implements LenderOffersParser {

	protected final static String LOG_NAME_EMPTY = "parser.name.empty";

	protected final static String LOG_RATE_INVALID = "parser.rate.invalid";

	protected final static String LOG_RATE_EMPTY = "parser.rate.empty";

	protected final static String LOG_AVAILABLE_INVALID = "parser.available.invalid";

	protected final static String LOG_AVAILABLE_EMPTY = "parser.available.empty";

	private final static Logger LOGGER = Logger.getLogger(LenderOffersCsvParserBean.class.getName());

	@Autowired
	private MessageSourceUtil messageSource;

	protected Scanner rowScanner;

	protected String line;

	protected LenderOffer lenderOffer;

	@Override
	public List<LenderOffer> parse(File marketFileCsv) throws FileNotFoundException {
		List<LenderOffer> lenderOffers = new LinkedList<LenderOffer>();

		try (Scanner scanner = new Scanner(marketFileCsv);) {
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				getRecordFromLine();
				if (lenderOffer != null) {
					lenderOffers.add(lenderOffer);
				}
				line = null;
				lenderOffer = null;
			}
		}

		return lenderOffers;
	}

	protected void getRecordFromLine() {
		try {
			rowScanner = new Scanner(line);
			rowScanner.useDelimiter(",");
			lenderOffer = new LenderOffer();

			parseName();
			if (lenderOffer.getName() == null) {
				lenderOffer = null;
				return;
			}

			parseRate();
			if (lenderOffer.getRate() == null) {
				lenderOffer = null;
				return;
			}

			parseAvailable();
			if (lenderOffer.getAvailable() == null) {
				lenderOffer = null;
				return;
			}

		} finally {
			if (rowScanner != null) {
				rowScanner.close();
				rowScanner = null;
			}
		}
	}

	protected void parseName() {
		if (rowScanner.hasNext()) {
			lenderOffer.setName(rowScanner.next());
		} else {
			LOGGER.info(logParseError(LOG_NAME_EMPTY));
		}
	}

	protected void parseRate() {
		if (rowScanner.hasNext()) {
			String rate = rowScanner.next();
			try {
				lenderOffer.setRate(new BigDecimal(rate));
			} catch (NumberFormatException e) {
				LOGGER.info(logParseError(LOG_RATE_INVALID));
			}
		} else {
			LOGGER.info(logParseError(LOG_RATE_EMPTY));
		}
	}

	protected void parseAvailable() {
		if (rowScanner.hasNext()) {
			String available = rowScanner.next();
			try {
				lenderOffer.setAvailable(Integer.valueOf(available));
			} catch (NumberFormatException e) {
				LOGGER.info(logParseError(LOG_AVAILABLE_INVALID));
			}
		} else {
			LOGGER.info(logParseError(LOG_AVAILABLE_EMPTY));
		}
	}

	protected String logParseError(String message) {
		return (new StringBuilder(getMessage(message)))//
				.append(" [record=").append(line)//
				.append(", name=").append(lenderOffer.getName())//
				.append(", rate=").append(lenderOffer.getRate())//
				.append(", available=").append(lenderOffer.getAvailable()).append("]")//
				.toString();
	}

	protected String getMessage(String code) {
		if (messageSource != null) {
			return messageSource.getMessage(code);
		} else {
			return code;
		}
	}

}
