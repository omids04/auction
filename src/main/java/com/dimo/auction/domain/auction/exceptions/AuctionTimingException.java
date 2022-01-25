package com.dimo.auction.domain.auction.exceptions;

import java.time.Duration;
import java.time.LocalDateTime;

public class AuctionTimingException extends RuntimeException{

    private final LocalDateTime startTime;
    private final Duration duration;
    public AuctionTimingException(LocalDateTime startTime, Duration duration) {
        super("Start time or Start time + Duration shouldn't be outside allowed hours." +
                " allowed hours are between 8 AM and 18 PM");
        this.startTime = startTime;
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }
}
