package com.dimo.auction.domain.auction.specs;

import com.dimo.auction.domain.shared.AbstractSpecification;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AuctionTimingSpec extends AbstractSpecification<LocalDateTime> {


    private static final AuctionTimingSpec instance = new AuctionTimingSpec();

    private final LocalTime allowedStartTime;
    private final LocalTime allowedEndTime;

    private AuctionTimingSpec() {
        allowedStartTime = LocalTime.of(8, 0);
        allowedEndTime = LocalTime.of(18, 0);
    }

    @Override
    public boolean isSatisfiedBy(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        return time.isAfter(allowedStartTime)
                && time.isBefore(allowedEndTime)
                && dateTime.isAfter(LocalDateTime.now());
    }

    public static AuctionTimingSpec getInstance(){
        return instance;
    }
}
