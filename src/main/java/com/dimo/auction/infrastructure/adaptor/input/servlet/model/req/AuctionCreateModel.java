package com.dimo.auction.infrastructure.adaptor.input.servlet.model.req;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AuctionCreateModel {

    private UUID accountId;
    private UUID itemId;
    private LocalDateTime startTime;
    private Duration duration;
    private BigInteger price;
}
