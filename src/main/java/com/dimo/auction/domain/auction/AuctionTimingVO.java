package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.exceptions.AuctionDurationException;
import com.dimo.auction.domain.auction.exceptions.AuctionTimingException;
import com.dimo.auction.domain.auction.specs.AuctionDurationSpec;
import com.dimo.auction.domain.auction.specs.AuctionTimingSpec;

import java.time.Duration;
import java.time.LocalDateTime;

public class AuctionTimingVO {
    private final LocalDateTime startTime;
    private final Duration duration;

    public AuctionTimingVO(LocalDateTime startTime, Duration duration){
        if(!AuctionTimingSpec.getInstance().isSatisfiedBy(startTime)
            || !AuctionTimingSpec.getInstance().isSatisfiedBy(startTime.plus(duration)))
        {
            throw new AuctionTimingException(startTime, duration);
        }
        if (!AuctionDurationSpec.getInstance().isSatisfiedBy(duration))
        {
            throw new AuctionDurationException(duration);
        }
        this.startTime = startTime;
        this.duration = duration;
    }

    public LocalDateTime getStartTime(){
        return this.startTime;
    }

    public LocalDateTime getEndTime(){
        return this.startTime.plus(duration);
    }
}
