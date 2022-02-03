package com.dimo.auction.infrastructure.adaptor.input.servlet.model.res;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.UUID;

@Setter
@Getter
public class BidCreateModel {

    private UUID auctionId;
    private UUID accountId;
    private BigInteger price;
}
