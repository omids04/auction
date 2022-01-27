package com.dimo.auction.domain.auction.exceptions;

import com.dimo.auction.domain.auction.vos.Price;
import lombok.Getter;

@Getter
public class BidPriceException extends RuntimeException {
    private final Price currentHighest;
    private final Price bidPrice;
    public BidPriceException(Price currentHighest, Price bidPrice) {
        super("problem in bid pricing");
        this.bidPrice = bidPrice;
        this.currentHighest = currentHighest;
    }
}
