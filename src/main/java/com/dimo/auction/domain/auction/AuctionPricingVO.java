package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.exceptions.AuctionBasePricingException;
import com.dimo.auction.domain.auction.specs.AuctionBasePriceSpec;

import java.math.BigInteger;

public class AuctionPricingVO {

    private BigInteger currentHighestBid;
    private BigInteger basePrice;

    public AuctionPricingVO(BigInteger basePrice) {
        setBasePrice(basePrice);
        this.currentHighestBid = basePrice;
    }

    public BigInteger getBasePrice(){
        return this.basePrice;
    }

    public BigInteger getCurrentHighestBid(){
        return this.currentHighestBid;
    }

    private void setBasePrice(BigInteger basePrice){
        if(!AuctionBasePriceSpec.getInstance().isSatisfiedBy(basePrice))
            throw new AuctionBasePricingException(basePrice);
        this.basePrice = basePrice;
    }
}
