package com.zopa.borrowercalc.borrowercalc.repositories;

import java.util.Collection;
import java.util.Set;

import com.zopa.borrowercalc.model.entities.LenderOffer;

public interface LenderOfferRepository {

	Set<LenderOffer> findAllOrderByRateAsc();

	void setLenderOffers(Collection<LenderOffer> lenderOffers);

}
