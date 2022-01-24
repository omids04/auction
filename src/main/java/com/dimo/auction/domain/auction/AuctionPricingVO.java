package com.dimo.auction.domain.auction;

import java.math.BigInteger;

public class AuctionPricingVO {

    private BigInteger currentHighestBid;
    private BigInteger basePrice;

    public AuctionPricingVO(BigInteger basePrice) {
        this.basePrice = basePrice;
        this.currentHighestBid = basePrice;
    }
}
