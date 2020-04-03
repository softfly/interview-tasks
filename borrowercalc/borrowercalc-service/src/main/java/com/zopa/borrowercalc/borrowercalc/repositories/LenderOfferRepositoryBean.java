package com.zopa.borrowercalc.borrowercalc.repositories;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Repository;

import com.zopa.borrowercalc.borrowercalc.comparators.RateAscLenderOfferComparator;
import com.zopa.borrowercalc.model.entities.LenderOffer;

@Repository
public class LenderOfferRepositoryBean implements LenderOfferRepository {

	private TreeSet<LenderOffer> lenderOffers = init();

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	protected TreeSet<LenderOffer> init() {
		return new TreeSet<LenderOffer>(new RateAscLenderOfferComparator());
	}

	public Set<LenderOffer> findAllOrderByRateAsc() {
		try {
			lock.readLock().lock();
			return detach(lenderOffers);
		} finally {
			lock.readLock().unlock();
		}
	}

	protected Set<LenderOffer> detach(Set<LenderOffer> lenderOffers) {
		try {
			TreeSet<LenderOffer> cloneLenderOffers = init();
			for (LenderOffer lenderOffer : lenderOffers) {
				cloneLenderOffers.add(lenderOffer.clone());
			}
			return cloneLenderOffers;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setLenderOffers(Collection<LenderOffer> lenderOffers) {
		lock.writeLock().lock();
		this.lenderOffers.clear();
		this.lenderOffers.addAll(lenderOffers);
		lock.writeLock().unlock();
	}

}
