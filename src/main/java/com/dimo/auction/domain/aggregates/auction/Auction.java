package com.dimo.auction.domain.aggregates.auction;

public class Auction {

    private AuctionId id;
    private ItemVO item;
    private AuctionPricingVO pricing;
    private AuctionTimingVO timing;

    public Auction(ItemVO item, AuctionPricingVO pricing, AuctionTimingVO timing) {
        this.item = item;
        this.pricing = pricing;
        this.timing = timing;
        this.id = new AuctionId();
    }
}
