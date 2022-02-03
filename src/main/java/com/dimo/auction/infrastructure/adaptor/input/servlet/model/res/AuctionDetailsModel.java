package com.dimo.auction.infrastructure.adaptor.input.servlet.model.res;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class AuctionDetailsModel {

    private UUID id;
    private UUID accountId;
    private UUID itemId;
    private int bidCount;
    private LocalDateTime startTime;
    private long duration;
    private BigInteger basePrice;
    private BigInteger currentHighest;
}
