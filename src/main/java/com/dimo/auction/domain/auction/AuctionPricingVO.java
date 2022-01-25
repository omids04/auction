package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.exceptions.AuctionBasePricingException;
import com.dimo.auction.domain.auction.specs.AuctionBasePriceSpec;
import com.dimo.auction.domain.shared.ValueObject;

import java.math.BigInteger;

public class AuctionPricingVO extends ValueObject {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionPricingVO that = (AuctionPricingVO) o;

        if (!currentHighestBid.equals(that.currentHighestBid)) return false;
        return basePrice.equals(that.basePrice);
    }

    @Override
    public int hashCode() {
        int result = currentHighestBid.hashCode();
        result = 31 * result + basePrice.hashCode();
        return result;
    }
}
