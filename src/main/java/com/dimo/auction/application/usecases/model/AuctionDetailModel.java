package com.dimo.auction.application.usecases.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class AuctionDetailModel {

    private UUID id;
    private UUID itemId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigInteger basePrice;
    private BigInteger currentHighestBid;
}
