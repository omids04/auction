package com.dimo.auction.domain.auction.specs;

import com.dimo.auction.domain.shared.AbstractSpecification;

import java.time.Duration;

public class AuctionDurationSpec extends AbstractSpecification<Duration> {

    private static final AuctionDurationSpec instance = new AuctionDurationSpec();

    public static AuctionDurationSpec getInstance() {
        return instance;
    }

    @Override
    public boolean isSatisfiedBy(Duration duration) {
        return !duration.minusMinutes(5).isNegative();
    }
}
