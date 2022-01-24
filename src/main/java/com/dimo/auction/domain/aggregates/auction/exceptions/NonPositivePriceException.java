package com.dimo.auction.domain.aggregates.auction.exceptions;

import java.math.BigInteger;

public class NonPositivePriceException extends RuntimeException{
    private final BigInteger price;

    public NonPositivePriceException(BigInteger price) {
        super("price should be more than 0");
        this.price = price;
    }

    public BigInteger getPrice() {
        return price;
    }
}
