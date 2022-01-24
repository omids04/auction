package com.dimo.auction.application.usecases.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class AuctionCreateModel {

    private UUID itemId;
    private LocalDateTime startTime;
    private Duration auctionDuration;
    private BigInteger basePrice;
}
