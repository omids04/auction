package com.dimo.auction.domain.auction.exceptions;

import java.time.Duration;

public class AuctionDurationException extends RuntimeException{

    private final Duration duration;

    public AuctionDurationException(Duration duration) {
        super("Auction duration should be more than 5 minutes");
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }
}
