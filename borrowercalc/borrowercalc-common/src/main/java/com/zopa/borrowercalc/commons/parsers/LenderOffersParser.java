package com.zopa.borrowercalc.commons.parsers;

import java.io.File;
import java.util.List;

import com.zopa.borrowercalc.model.entities.LenderOffer;

public interface LenderOffersParser {

	List<LenderOffer> parse(File marketFile) throws Exception;

}
