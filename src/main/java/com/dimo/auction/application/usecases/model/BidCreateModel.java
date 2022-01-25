package com.dimo.auction.application.usecases.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class BidCreateModel {

    private BigDecimal price;
    private UUID auctionId;
    private UUID accountId;
}
