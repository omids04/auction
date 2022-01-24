package com.dimo.auction.domain.services;

import com.dimo.auction.domain.aggregates.auction.Auction;
import com.dimo.auction.domain.aggregates.auction.AuctionPricingVO;
import com.dimo.auction.domain.aggregates.auction.AuctionTimingVO;
import com.dimo.auction.domain.aggregates.auction.ItemVO;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuctionOperations {

    private static final AuctionOperations instance = new AuctionOperations();

    private AuctionOperations(){

    }

    public static AuctionOperations getInstance() {
        return instance;
    }

    public Auction createAuction(UUID itemId, BigInteger basePrice, LocalDateTime startTime, Duration duration) {
        AuctionTimingVO timing = new AuctionTimingVO(startTime, duration);
        AuctionPricingVO pricing = new AuctionPricingVO(basePrice);
        ItemVO item = new ItemVO(itemId);
        return new Auction(item,pricing, timing);
    }


}
