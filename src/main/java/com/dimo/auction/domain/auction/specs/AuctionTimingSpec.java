package com.dimo.auction.domain.auction.specs;

import com.dimo.auction.domain.auction.exceptions.AuctionTimingException;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.shared.AbstractSpecification;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AuctionTimingSpec extends AbstractSpecification<AuctionTiming> {


    private static final AuctionTimingSpec instance = new AuctionTimingSpec();

    private final LocalTime allowedStartTime;
    private final LocalTime allowedEndTime;
    private final Duration minAllowedDuration;

    private AuctionTimingSpec() {
        allowedStartTime = LocalTime.of(8, 0);
        allowedEndTime = LocalTime.of(18, 0);
        minAllowedDuration = Duration.ofMinutes(5);
    }

    @Override
    public boolean isSatisfiedBy(AuctionTiming timing) {
        return isInAllowedHours(timing.getStartTime())
                && isDurationLongEnough(timing.getDuration());
    }

    public void check(AuctionTiming timing){
        if(!isSatisfiedBy(timing))
            throw new AuctionTimingException(timing.getStartTime(), timing.getDuration());
    }

    private boolean isInAllowedHours(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        return time.isAfter(allowedStartTime)
                && time.isBefore(allowedEndTime);
    }

    private boolean isDurationLongEnough(Duration duration){
        return !(duration.compareTo(minAllowedDuration) < 0);
    }


    public static AuctionTimingSpec getInstance(){
        return instance;
    }
}
