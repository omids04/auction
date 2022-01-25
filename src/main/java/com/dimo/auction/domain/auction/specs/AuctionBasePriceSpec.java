package com.dimo.auction.domain.auction.specs;

import com.dimo.auction.domain.shared.AbstractSpecification;

import java.math.BigInteger;

public class AuctionBasePriceSpec extends AbstractSpecification<BigInteger> {

    private static final AuctionBasePriceSpec instance = new AuctionBasePriceSpec();

    public static AuctionBasePriceSpec getInstance() {
        return instance;
    }

    @Override
    public boolean isSatisfiedBy(BigInteger bigInteger) {
        return bigInteger.compareTo(BigInteger.ZERO) > 0;
    }
}
