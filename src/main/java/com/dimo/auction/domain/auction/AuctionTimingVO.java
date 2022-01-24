package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.exceptions.AuctionNotWithinAllowedHoursException;
import com.dimo.auction.domain.auction.specs.AllowedHoursSpec;

import java.time.Duration;
import java.time.LocalDateTime;

public class AuctionTimingVO {
    private LocalDateTime startTime;
    private Duration duration;

    public AuctionTimingVO(LocalDateTime startTime, Duration duration){
        if(!AllowedHoursSpec.getInstance().isSatisfiedBy(startTime)
        || !AllowedHoursSpec.getInstance().isSatisfiedBy(startTime.plus(duration)))
            throw new AuctionNotWithinAllowedHoursException(startTime, duration);
        this.startTime = startTime;
        this.duration = duration;
    }
}
