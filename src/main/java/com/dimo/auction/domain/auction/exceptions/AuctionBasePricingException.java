package com.dimo.auction.domain.auction.exceptions;

import java.math.BigInteger;

public class AuctionBasePricingException extends RuntimeException{
    private final BigInteger price;

    public AuctionBasePricingException(BigInteger price) {
        super("Base price should be more than 0");
        this.price = price;
    }

    public BigInteger getPrice() {
        return price;
    }
}
